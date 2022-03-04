package sysc4806.sysc4806_spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/rest/api")
public class BuddyInfoController {
    @Autowired
    private AddressBookRepository addressBookRepository;
    @Autowired
    private BuddyInfoRepository buddyInfoRepository;

    @GetMapping("/addressbooks/{addressbookId}/buddies")
    public ResponseEntity<List<BuddyInfo>> getAllBuddiesByTutorialId(@PathVariable("addressbookId") long addressbookId) {
        if (!addressBookRepository.existsById(addressbookId)) {
            throw new RuntimeException("addressbook not found");
        }
        List<BuddyInfo> buddies = buddyInfoRepository.findByAddressBookId(addressbookId);
        return new ResponseEntity<>(buddies, HttpStatus.OK);
    }
    @GetMapping("/buddies/{id}")
    public ResponseEntity<BuddyInfo> getBuddyById(@PathVariable("id") long id) {
        BuddyInfo buddyInfo = buddyInfoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("addressbook not found"));
        return new ResponseEntity<>(buddyInfo, HttpStatus.OK);
    }
    @PostMapping("/addressbooks/{addressbooksId}/buddies")
    public ResponseEntity<BuddyInfo> createBuddy(@PathVariable("addressbooksId") long addressbooksId,
                                                 @RequestBody BuddyInfo BuddyRequest) {
        BuddyInfo buddyInfo = addressBookRepository.findById(addressbooksId).map(addressbook -> {
            BuddyRequest.setAddressBook(addressbook);
            return buddyInfoRepository.save(BuddyRequest);
        }).orElseThrow(() -> new RuntimeException("addressbook not found"));
        return new ResponseEntity<>(buddyInfo, HttpStatus.CREATED);
    }
    @PutMapping("/buddies/{id}")
    public ResponseEntity<BuddyInfo> updateBuddy(@PathVariable("id") long id, @RequestBody BuddyInfo BuddyRequest) {
        BuddyInfo buddyInfo = buddyInfoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("addressbook not found"));
        buddyInfo.setName(BuddyRequest.getName());
        buddyInfo.setPhone_number(BuddyRequest.getPhone_number());
        return new ResponseEntity<>(buddyInfoRepository.save(buddyInfo), HttpStatus.OK);
    }
    @DeleteMapping("/buddies/{id}")
    public ResponseEntity<HttpStatus> deleteBuddy(@PathVariable("id") long id) {
        buddyInfoRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/addressbooks/{addressbooksId}/buddies")
    public ResponseEntity<List<BuddyInfo>> deleteAllBuddiesOfAddressBook(@PathVariable("addressbooksId") long addressbooksId) {
        if (!addressBookRepository.existsById(addressbooksId)) {
            throw new RuntimeException("addressbook not found");
        }
        buddyInfoRepository.deleteByAddressBookId(addressbooksId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
