package sysc4806.sysc4806_spring;

import org.springframework.data.jpa.repository.JpaRepository;


import javax.transaction.Transactional;
import java.util.List;

public interface BuddyInfoRepository extends JpaRepository<BuddyInfo, Long> {
    List<BuddyInfo> findByAddressBookId(Long id);
    BuddyInfo findByName(String name);
    //BuddyInfo findByPhone_Number(String phone_number);

    @Transactional
    void deleteByAddressBookId(Long addressbookId);
}