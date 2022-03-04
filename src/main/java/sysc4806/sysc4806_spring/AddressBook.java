package sysc4806.sysc4806_spring;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "addressbooks")
public class AddressBook implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "Name")
    private String addressbookName;


    public AddressBook(){
    }

    public AddressBook(String addressbookName){
       this.addressbookName = addressbookName;
    }

    public Long getId() {
        return id;
    }

    public String getAddressbookName() {
        return addressbookName;
    }

    public void setAddressbookName(String addressbookName) {
        this.addressbookName = addressbookName;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return String.format(
                "AddressBook[id=%d , addressbookNumber=%s]",
                id, addressbookName);
    }
}

