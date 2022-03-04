package sysc4806.sysc4806_spring;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/rest/api")
public class AddressBookController {
    @Autowired
    AddressBookRepository addressBookRepository;
    @GetMapping("/addressbooks")
    public ResponseEntity<List<AddressBook>> getAllAddressBooks(@RequestParam(required = false) String name){
        List<AddressBook> addressBooks = new ArrayList<AddressBook>();
        if(name == null){
            addressBookRepository.findAll().forEach(addressBooks::add);
        }
        else{
            addressBookRepository.findByAddressbookName(name).forEach(addressBooks::add);
        }
        if(addressBooks.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(addressBooks,HttpStatus.OK);
    }

    @GetMapping("/addressbooks/{id}")
    public ResponseEntity<AddressBook> getAddressBooksById(@PathVariable("id") long id) {
        AddressBook addressBook = addressBookRepository.findById(id).orElseThrow(() -> new RuntimeException("addressbook not found"));
        return new ResponseEntity<>(addressBook, HttpStatus.OK);
    }

    @PostMapping("/addressbooks")
    public ResponseEntity<AddressBook> createAddressBooks(@RequestBody AddressBook addressBook) {
        AddressBook addressBookTemp = addressBookRepository.save(new AddressBook(addressBook.getAddressbookName()));
        return new ResponseEntity<>(addressBookTemp, HttpStatus.CREATED);
    }
    @PutMapping("/addressbooks/{id}")
    public ResponseEntity<AddressBook> updateAddressBooks(@PathVariable("id") long id, @RequestBody AddressBook addressBook) {
        AddressBook addressBookTemp = addressBookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("addressbook not found"));
        addressBookTemp.setAddressbookName(addressBook.getAddressbookName());
        return new ResponseEntity<>(addressBookRepository.save(addressBookTemp), HttpStatus.OK);
    }
    @DeleteMapping("/addressbooks/{id}")
    public ResponseEntity<HttpStatus> deleteAddressBooks(@PathVariable("id") long id) {
        addressBookRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/addressbooks")
    public ResponseEntity<HttpStatus> deleteAllAddressBooks() {
        addressBookRepository.deleteAll();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
