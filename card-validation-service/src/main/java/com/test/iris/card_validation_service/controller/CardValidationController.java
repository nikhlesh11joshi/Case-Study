package com.test.iris.card_validation_service.controller;


import com.test.iris.card_validation_service.model.Cards;
import com.test.iris.card_validation_service.request.CardDetailsRequest;
import com.test.iris.card_validation_service.response.CardDetailsResponse;
import com.test.iris.card_validation_service.response.CardValidationResponse;
import com.test.iris.card_validation_service.service.CardValidationService;
import com.test.iris.card_validation_service.util.CardValidationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Card Validation Service",
        description = "APIs for validating card details")
@RestController
public class CardValidationController {
    private Logger logger = org.slf4j.LoggerFactory.getLogger(CardValidationController.class);

    @Autowired
    private CardValidationService cardValidationService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Validate Card", description = "Validate the card details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card validated"),
            @ApiResponse(responseCode = "404", description = "Card not validated")
    })
    @PostMapping("/validate-card")
    public ResponseEntity<CardValidationResponse> validateCard(@Valid @RequestBody CardDetailsRequest cardDetailsRequst) {
        logger.info("Validating card for card number: {}", CardValidationUtil.maskCardNumber(cardDetailsRequst.getCardNumber()));
        CardValidationResponse cardValidationResponse = cardValidationService.validateCard(cardDetailsRequst);
        logger.info("Card validation response: {}", cardValidationResponse);
        return ResponseEntity.ok(cardValidationResponse);
    }

    @Operation(summary = "Create Card", description = "Create the card details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card created"),
            @ApiResponse(responseCode = "404", description = "Card not created")
    })
    @PostMapping("/create-card")
    public ResponseEntity<CardDetailsResponse> saveCardofUser(@Valid @RequestBody CardDetailsRequest cardDetailsRequst) {
        logger.info("Validating card for card number: {}", CardValidationUtil.maskCardNumber(cardDetailsRequst.getCardNumber()));
        Cards cards =   cardValidationService.saveCard(cardDetailsRequst);
        CardDetailsResponse cardDetailsResponse = modelMapper.map(cards, CardDetailsResponse.class);
         logger.info("Card validation response: {}", cardDetailsResponse);
        return ResponseEntity.ok(cardDetailsResponse);
    }

    @Operation(summary = "Get Card by userId", description = "Get the card details by userId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card found"),
            @ApiResponse(responseCode = "404", description = "Card not found")
    })
    @GetMapping("/userId/{userId}")
    public ResponseEntity<CardDetailsResponse> getCardByuserId(@NotNull @PathVariable Long userId) {
        logger.info("Get the card details for userId: {}", userId);
       return cardValidationService.getCardByuserId(userId);
    }

    @Operation(summary = "Update Card by userId", description = "Update the card details by userId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card updated"),
            @ApiResponse(responseCode = "404", description = "Card not updated")
    })
    @PutMapping("/userId/{userId}")
    public ResponseEntity<CardDetailsResponse> updateCardByUserId(@NotNull @PathVariable Long userId, @Valid  @RequestBody CardDetailsRequest cardDetailsRequst) {
        logger.info("Update the card details for userId: {}", userId);
        return cardValidationService.updateCardByUserId(userId, cardDetailsRequst);
    }

    @Operation(summary = "Delete Card by userId", description = "Delete the card details by userId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card deleted"),
            @ApiResponse(responseCode = "404", description = "Card not deleted")
    })
    @DeleteMapping("/userId/{userId}")
    public ResponseEntity<CardDetailsResponse> deleteCardByUserId(@NotNull @PathVariable Long userId) {
        logger.info("Delete the card details for userId: {}", userId);
        return cardValidationService.deleteCardByUserId(userId);
    }

    @Operation(summary = "Get all card details", description = "Get all the card details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card details found"),
            @ApiResponse(responseCode = "404", description = "Card details not found")
    })
    @GetMapping("/")
    public ResponseEntity<List<CardDetailsResponse>> getAllCardDetails() {
        logger.info("Get all card details");
        return cardValidationService.getAllCardDetails();
    }
}
