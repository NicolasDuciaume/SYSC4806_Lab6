package sysc4806.sysc4806_spring;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressBookRepository extends JpaRepository<AddressBook, Long> {
    List<AddressBook> findByAddressbookName(String addressbookName);
}
