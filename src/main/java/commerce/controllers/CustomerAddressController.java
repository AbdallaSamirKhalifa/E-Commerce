package commerce.controllers;

import commerce.controllers.assemblers.CustomerAddressAssembler;
import commerce.dto.request.AddressRequest;
import commerce.dto.response.AddressResponse;
import commerce.service.contract.ICustomerAddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer/address")
@RequiredArgsConstructor
public class CustomerAddressController {
    private final ICustomerAddressService addressService;
    private final CustomerAddressAssembler assembler;


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{addressId}")
    public ResponseEntity<EntityModel<AddressResponse>> getAddressById(@PathVariable Integer addressId){
        return ResponseEntity.ok(
                assembler.toModel(
                        addressService.getAddressById(addressId)
                )
        );
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public ResponseEntity<EntityModel<AddressResponse>> createNewAddress(@RequestBody @Valid AddressRequest request) {
        return ResponseEntity.ok(
                assembler.toModel(addressService.createNewAddress(request))
        );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/{addressId}")
    public ResponseEntity<EntityModel<AddressResponse>> updateAddress(@PathVariable() Integer addressId,
                                                                      @RequestBody @Valid AddressRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                assembler.
                        toModel(addressService.updateAddressById(addressId, request))
        );
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Integer addressId) {
        addressService.deleteAddressById(addressId);
        return ResponseEntity.noContent().build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    public ResponseEntity<CollectionModel<EntityModel<AddressResponse>>> getAllAddresses() {
        return ResponseEntity.ok(
                assembler.toCollectionModel(addressService.getAllAddresses())
        );
    }


}
