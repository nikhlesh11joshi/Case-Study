package com.test.iris.card_validation_service.service;

import com.test.iris.card_validation_service.exception.CardAlreadyExistForUserException;
import com.test.iris.card_validation_service.exception.InvalidCardException;
import com.test.iris.card_validation_service.exception.NoCardFoundException;
import com.test.iris.card_validation_service.exception.UserNoFoundException;
import com.test.iris.card_validation_service.model.Cards;
import com.test.iris.card_validation_service.repository.CardRepository;
import com.test.iris.card_validation_service.request.CardDetailsRequest;
import com.test.iris.card_validation_service.response.CardDetailsResponse;
import com.test.iris.card_validation_service.response.CardValidationResponse;
import com.test.iris.card_validation_service.util.CardValidationUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.smartcardio.Card;
import java.time.DateTimeException;
import java.time.YearMonth;
import java.util.List;

@Service
public class CardValidationService {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(CardValidationService.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CardRepository cardRepository;

    public CardValidationResponse validateCard(CardDetailsRequest cardDetailsRequst,String bearerToken) {
        logger.info("Validating card for card number: {}", CardValidationUtil.maskCardNumber(cardDetailsRequst.getCardNumber()));

        boolean isCardValid=false;
        String cardNumber = cardDetailsRequst.getCardNumber();
        String expiryMonth = cardDetailsRequst.getExpirationMonth();
        String expiryYear = cardDetailsRequst.getExpirationYear();
        String cvv = cardDetailsRequst.getCvv();
        String cardType = cardDetailsRequst.getCardType();

        if (cardNumber == null || cardNumber.isEmpty()) {
            return new CardValidationResponse(false, "Card number is required");
        }

        if (expiryYear == null || expiryYear.isEmpty()) {
            return new CardValidationResponse(false, "Expiry Year is required");
        }
        if (expiryMonth == null || expiryMonth.isEmpty()) {
            return new CardValidationResponse(false, "Expiry Month is required");
        }

        if (cvv == null || cvv.isEmpty()) {
            return new CardValidationResponse(false, "CVV is required");
        }

        if (cardNumber.length() != 16) {
            return new CardValidationResponse(false, "Card number should be 16 digits");
        }

        if (cvv.length() != 3) {
            return new CardValidationResponse(false, "CVV should be 3 digits");
        }

        try {
            isCardValid =(CardValidationUtil.isValidCardNumber(cardNumber) && CardValidationUtil.validateCardNotExpired(expiryMonth, expiryYear) && CardValidationUtil.isValidCardType(cardNumber, cardType));
            logger.info("Card validation done : {} isCardValid : ", isCardValid);
            if(isCardValid) {
                /*Cards card = modelMapper.map(cardDetailsRequst, Cards.class);
                cardRepository.save(card);
                logger.info("Card saved successfully");*/
                return new CardValidationResponse(true, "Card is valid");

            }
           else {
                return new CardValidationResponse(false, "Card is invalid");
            }
        } catch (DateTimeException e) {
            return new CardValidationResponse(false, "Invalid expiry date|| InvalidCard");
        }

    }

    public Cards saveCard(CardDetailsRequest cardDetailsRequst,String authorizationHeader) {
        boolean isCardExistForuserId=false;
        logger.info("Save the card details for userId: {}", cardDetailsRequst.getUserId());
        Long userId = cardDetailsRequst.getUserId();
        CardValidationResponse cardValidationResponse = validateCard(cardDetailsRequst,authorizationHeader);
            if(cardValidationResponse.isValid()) {
                if (userId != null) {
                    isCardExistForuserId = cardRepository.existsByUserId(userId);
                    if (!isCardExistForuserId) {
                        Cards cards = modelMapper.map(cardDetailsRequst, Cards.class);
                        logger.info("Card saved successfully for the user id: {}", cardDetailsRequst.getUserId());
                        return cardRepository.save(cards);

                    } else {
                        throw new CardAlreadyExistForUserException("Card already exist for the user id: " + userId);
                    }

                } else {
                    throw new UserNoFoundException("User not found for the user id: " + userId);
                }
            }
            else {
                throw new InvalidCardException("Invalid card details !! Bad request");
            }

    }

     public ResponseEntity<CardDetailsResponse> getCardByuserId(Long userId) {
        logger.info("Get the card details for userId: {}", userId);
         try {
             if (userId != null) {
                 Cards cards = cardRepository.findByUserId(userId);
                 if (cards != null) {
                     CardDetailsResponse cardDetailsResponse = modelMapper.map(cards, CardDetailsResponse.class);
                     logger.info("Card details found for the user id: {}", userId);
                     return ResponseEntity.ok(cardDetailsResponse);
                 } else {
                     throw new NoCardFoundException("No card found for the user id: " + userId);

                 }

             } else {
                 throw new NoCardFoundException("User id is required");
             }
         }
        catch(Exception ex){
            logger.error("No card found for the user id: {}", userId);
            return ResponseEntity.notFound().build();
        }

      }

    public ResponseEntity<CardDetailsResponse> updateCardByUserId(@NotNull Long userId, @Valid CardDetailsRequest cardDetailsRequst) {
        logger.info("Update the card details for userId: {}", userId);
        try {
            if (userId != null) {
                Cards cards = cardRepository.findByUserId(userId);
                if (cards != null) {
                    cards.setCardNumber(cardDetailsRequst.getCardNumber());
                    cards.setCardHolderName(cardDetailsRequst.getCardHolderName());
                    cards.setCardType(cardDetailsRequst.getCardType());
                    cards.setExpirationMonth(cardDetailsRequst.getExpirationMonth());
                    cards.setExpirationYear(cardDetailsRequst.getExpirationYear());
                    cards.setCvv(cardDetailsRequst.getCvv());
                    cardRepository.save(cards);
                    logger.info("Card details updated successfully for the user id: {}", userId);
                    CardDetailsResponse cardDetailsResponse = modelMapper.map(cards, CardDetailsResponse.class);
                    return ResponseEntity.ok(cardDetailsResponse);
                } else {
                    throw new NoCardFoundException("No card found for the user id: " + userId);
                }
            } else {
                throw new NoCardFoundException("User id is required");
            }
        } catch (Exception ex) {
            logger.error("No card found for the user id: {}", userId);
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<CardDetailsResponse> deleteCardByUserId(@NotNull Long userId) {
        logger.info("Delete the card details for userId: {}", userId);
        try {
            if (userId != null) {
                Cards cards = cardRepository.findByUserId(userId);
                if (cards != null) {
                    cardRepository.delete(cards);
                    logger.info("Card details deleted successfully for the user id: {}", userId);
                    CardDetailsResponse cardDetailsResponse = modelMapper.map(cards, CardDetailsResponse.class);
                    return ResponseEntity.ok(cardDetailsResponse);
                } else {
                    throw new NoCardFoundException("No card found for the user id: " + userId);
                }
            } else {
                throw new NoCardFoundException("User id is required");
            }
        } catch (Exception ex) {
            logger.error("No card found for the user id: {}", userId);
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<List<CardDetailsResponse>> getAllCardDetails() {
        logger.info("Get all card details");
        List<Cards> cards = cardRepository.findAll();
        List<CardDetailsResponse> cardDetailsResponses = modelMapper.map(cards, List.class);
        logger.info("All card details found: "+ cardDetailsResponses);
        return ResponseEntity.ok(cardDetailsResponses);
    }
}
