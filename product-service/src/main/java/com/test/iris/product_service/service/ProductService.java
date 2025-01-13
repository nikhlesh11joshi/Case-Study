package com.test.iris.product_service.service;

import com.test.iris.product_service.exception.ProductAlreadyExists;
import com.test.iris.product_service.exception.ProductNotFoundException;
import com.test.iris.product_service.model.Product;
import com.test.iris.product_service.repository.ProductRepository;
import com.test.iris.product_service.request.ProductDto;
import com.test.iris.product_service.request.UpdateProductDto;
import com.test.iris.product_service.response.NotificationResponse;
import com.test.iris.product_service.response.ProductAvailabilityListResponse;
import com.test.iris.product_service.response.ProductAvailabilityResponse;
import com.test.iris.product_service.response.ProductResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private static Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<ProductResponse> addProduct(ProductDto productDto) {
        logger.info("add product Request received : "+ productDto);
        Product existProductByProductId = productRepository.findByProductId(productDto.getProductId()).orElse(null);
        if(existProductByProductId!=null){
            logger.error("Product already exists with the given productId : "+ productDto.getProductId());
            /*existProductByProductId.setQuantity(existProductByProductId.getQuantity()+productDto.getQuantity());
            existProductByProductId.setPrice(productDto.getPrice());
            existProductByProductId.setCategory(productDto.getCategory());
            existProductByProductId.setName(productDto.getName());
            existProductByProductId.setDescription(productDto.getDescription());*/
            throw new ProductAlreadyExists("Product already exists with the given productId !! Please try to update the product  "+ productDto.getProductId());

        }

        Product product = modelMapper.map(productDto, Product.class);
       Product addedProduct= productRepository.save(product);
        ProductResponse productResponse = modelMapper.map(addedProduct, ProductResponse.class);
        logger.info("Product added successfully");
        return ResponseEntity.ok(productResponse);
    }

    public ResponseEntity<ProductResponse> getProductByName(String productName) {
        logger.info("Get product by name Request recieved : "+ productName);
        Product productFound= productRepository.findByName(productName);
        if (productFound == null) {
            logger.error("Product not found");
            throw new ProductNotFoundException("Product not found");
        }
        ProductResponse productResponse = modelMapper.map(productFound, ProductResponse.class);
        logger.info("Product found successfully");
        return ResponseEntity.ok(productResponse);
    }

    public ResponseEntity<ProductResponse> getProductByProductId(Long productId) {
        logger.info("Get product by name Request recieved : "+ productId);
        // Product product = modelMapper.map(productDto, Product.class);
        Product productFound= productRepository.findByProductId(productId).orElse(null);
        if (productFound == null) {
            logger.error("Product not found");
            throw new ProductNotFoundException("Product not found");
        }
        ProductResponse productResponse = modelMapper.map(productFound, ProductResponse.class);
        logger.info("Product found successfully");
        return ResponseEntity.ok(productResponse);
    }
    @Transactional
    public ResponseEntity<ProductResponse> deleteProductById(Long productId) {
        logger.info(" delete product By productId request recieved: "+ productId);
        // Product product = modelMapper.map(productDto, Product.class);
        Product productFound= productRepository.findByProductId(productId).orElse(null);
        if (productFound == null) {
            logger.error("Product not found");
            throw new ProductNotFoundException("Product not found");
        }
        productRepository.deleteByProductId(productId);
        ProductResponse productResponse = modelMapper.map(productFound, ProductResponse.class);
        logger.info("Product deleted  successfully");
        return ResponseEntity.ok(productResponse);
    }
    public ResponseEntity<ProductResponse> deleteProductByname(String productName) {
        logger.info(" delete product By name  request recieved: "+ productName);
        Product productFound= productRepository.findByName(productName);
        if (productFound == null) {
            logger.error("Product not found");
            throw new ProductNotFoundException("Product not found");
        }
        productRepository.delete(productFound);
        ProductResponse productResponse = modelMapper.map(productFound, ProductResponse.class);
        logger.info("Product deleted  successfully");
        return ResponseEntity.ok(productResponse);
    }
    public ResponseEntity<ProductResponse> updateProduct(UpdateProductDto updateProductDto, Long productId) {
        logger.info(" update product request recieved: "+ updateProductDto);
       // Product product = modelMapper.map(productDto, Product.class);
        Product productFound= productRepository.findByProductId(productId).orElse(null);
        if (productFound == null) {
            logger.error("Product not found");
            throw new ProductNotFoundException("Product not found");
        }
        else {
            if(updateProductDto.getQuantity()!=null){
                productFound.setQuantity(productFound.getQuantity()+updateProductDto.getQuantity());
                logger.info("Product quantity updated successfully");
            }
            if (updateProductDto.getPrice()!=null){
                productFound.setPrice(updateProductDto.getPrice());
                logger.info("Product price updated successfully");
            }
            if (updateProductDto.getCategory()!=null){
                productFound.setCategory(updateProductDto.getCategory());
                logger.info("Product category updated successfully");
            }
            if (updateProductDto.getName()!=null){
                productFound.setName(updateProductDto.getName());
                logger.info("Product name updated successfully");
            }
            if (updateProductDto.getDescription()!=null){
                productFound.setDescription(updateProductDto.getDescription());
                logger.info("Product description updated successfully");
            }

/*
            productFound.setPrice(updateProductDto.getPrice());
            productFound.setCategory(updateProductDto.getCategory());
            productFound.setName(updateProductDto.getName());
            productFound.setDescription(updateProductDto.getDescription());*/
            productRepository.save(productFound);
        }
        ProductResponse productResponse = modelMapper.map(productFound, ProductResponse.class);
        logger.info("Product updated successfully");
        return ResponseEntity.ok(productResponse);
    }
    public ResponseEntity<List<ProductResponse>> fetchAllProducts() {
        logger.info("fetch all products request recieved");
        List<Product> products = productRepository.findAll();
        List<ProductResponse> productResponses = products.stream().map(product -> modelMapper.map(product, ProductResponse.class)).toList();
        logger.info("All Products fetched successfully");
        return ResponseEntity.ok(productResponses);
    }

    public ResponseEntity<List<ProductResponse>> fetchAllProdcutsByCategory(String category) {
        logger.info("fetch all products by category request recieved");
        List<Product> products = productRepository.findByCategory(category);
        if(products.isEmpty()){
            logger.error("Products not found with the given category : "+ category);
            throw new ProductNotFoundException("Products not found with the given category : "+ category);
        }
        List<ProductResponse> productResponses = products.stream().map(product -> modelMapper.map(product, ProductResponse.class)).toList();
        logger.info("All Products fetched successfully");
        return ResponseEntity.ok(productResponses);
    }
    public ResponseEntity<ProductAvailabilityResponse> checkProductAvailabilityandQty(Long productId,Long quantity){
        boolean isAvailableQuantity =false;
        Product updatedProduct = new Product();
            Optional<Product> product = productRepository.findByProductId(productId);
            logger.info("Product found : "+ product +"product.isPresent() :"+ product.isPresent());
            ProductAvailabilityResponse productAvailabilityResponse =  new ProductAvailabilityResponse();
            if(product.isPresent()){
                isAvailableQuantity=  product.get().getQuantity()>=quantity;
                if(isAvailableQuantity) {
                    logger.info("isAvailableQuantity : " + isAvailableQuantity);
                    product.get().setQuantity(product.get().getQuantity()-quantity);
                  updatedProduct=  productRepository.save(product.get());
                  logger.info("Product updated : "+ updatedProduct);
                    productAvailabilityResponse.setAvailable(isAvailableQuantity);
                    productAvailabilityResponse.setMessage("Required Quantity of the product is available!!Process the order");
                    return  ResponseEntity.ok(productAvailabilityResponse);
                }
                else {
                    logger.info("isAvailableQuantity : " + isAvailableQuantity);
                    productAvailabilityResponse.setAvailable(isAvailableQuantity);
                    productAvailabilityResponse.setMessage("Required Quantity of the product is not available!! Please retry the order After sometime!!");
                    return ResponseEntity.ok(productAvailabilityResponse);
                }

            }
            else {
                logger.info("Product not found with the given productId : "+ productId);
                productAvailabilityResponse.setAvailable(isAvailableQuantity);
                productAvailabilityResponse.setMessage("Required Quantity of the product is not available!! Please retry the order After sometime!!");
                return ResponseEntity.ok(productAvailabilityResponse);
               // throw new ProductNotFoundException("Product is out of stock or not available!! Please try again after sometime");
            }

    }

    public ResponseEntity<ProductAvailabilityListResponse> checkProductAvailabilityandQtyList(List<ProductDto> cartItems){
        boolean isAvailableQuantity =false;
        Map<Product, Boolean> productAvailabilityMap = new HashMap<>();
        ProductAvailabilityListResponse productAvailabilityResponse =  new ProductAvailabilityListResponse();
            logger.info("checkProductAvailability - Request recieved :  productId : "+cartItems);
            // Logic to check product availability
            if (cartItems.isEmpty()) {
                logger.error("Cart is empty");
                throw new ProductNotFoundException("Cart is empty");
            }
            for (ProductDto productDto : cartItems) {
                Optional<Product> product = productRepository.findByProductId(productDto.getProductId());
                logger.info("Product found : "+ product +"product.isPresent() :"+ product.isPresent());

                if(product.isPresent()){
                    isAvailableQuantity=  product.get().getQuantity()>=productDto.getQuantity();
                    if(isAvailableQuantity) {
                        logger.info("isAvailableQuantity : " + isAvailableQuantity);
                      //  product.get().setQuantity(product.get().getQuantity()-productDto.getQuantity());
                       // productRepository.save(product.get());
                        productAvailabilityMap.put(product.get(), isAvailableQuantity);
                      //  productAvailabilityResponse.setAvailable(isAvailableQuantity);
                        //productAvailabilityResponse.setMessage("Required Quantity of the product is available!!Process the order");
                    }
                    else {
                        productAvailabilityMap.put(product.get(), isAvailableQuantity);
                        logger.info("isAvailableQuantity : " + isAvailableQuantity);
                       // productAvailabilityResponse.setAvailable(isAvailableQuantity);
                       // productAvailabilityResponse.setMessage("Required Quantity of the product is not available!! Please retry the order After sometime!!");
                    }

                }
                else {
                    logger.info("Product not found with the given productId : "+ productDto.getProductId());
                    productAvailabilityResponse.setAvailable(isAvailableQuantity);
                    productAvailabilityResponse.setMessage("Required Quantity of the product is not available!! Please retry the order After sometime!!");
                     throw new ProductNotFoundException("Product is out of stock or not available!! Please try again after sometime");
                }
            }
            logger.info("Product availability map : "+ productAvailabilityMap);
            if(productAvailabilityMap.containsValue(false)){
                productAvailabilityResponse.setAvailable(false);
                productAvailabilityResponse.setMessage("Required Quantity of the product is not available!! Please retry the order After sometime!!");
              // convert productAvailabilityMap to List<ProductResponse>

                List<ProductResponse> productResponses = productAvailabilityMap.keySet().stream()
                        .map(product -> modelMapper.map(product, ProductResponse.class))
                        .collect(Collectors.toList());
                productAvailabilityResponse.setProducts(productResponses);
            }
            else {
                productAvailabilityResponse.setAvailable(true);
                productAvailabilityResponse.setMessage("Required Quantity of the product is available!!Process the order");
                List<ProductResponse> productResponses = productAvailabilityMap.keySet().stream()
                        .map(product -> modelMapper.map(product, ProductResponse.class))
                        .collect(Collectors.toList());
                productAvailabilityResponse.setProducts(productResponses);
            }


        return ResponseEntity.ok(productAvailabilityResponse);

    }

    public ResponseEntity<NotificationResponse> updateProductStatus(List<ProductDto> cartItems,String bearerToken) {

        logger.info("update product status request recieved : "+ cartItems);
        for (ProductDto productDto : cartItems) {
            Optional<Product> product = productRepository.findByProductId(productDto.getProductId());
            logger.info("Product found : "+ product +"product.isPresent() :"+ product.isPresent());
            if(product.isPresent()){
                product.get().setQuantity(product.get().getQuantity()-productDto.getQuantity());
                productRepository.save(product.get());
            }
            else {
                logger.info("Product not found with the given productId : "+ productDto.getProductId());
                throw new ProductNotFoundException("Product not found with the given productId : "+ productDto.getProductId());
            }
        }
        NotificationResponse notificationResponse = new NotificationResponse();
        notificationResponse.setMessage("Product status updated successfully");
        logger.info("Product status updated successfully");
        return ResponseEntity.ok(notificationResponse);
    }
}
