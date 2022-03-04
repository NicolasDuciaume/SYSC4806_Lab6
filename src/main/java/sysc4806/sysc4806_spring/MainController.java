package sysc4806.sysc4806_spring;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Controller
public class MainController {

    @GetMapping("/")
    public String MainMenu(){
        createAddress();
        return "redirect:/AddressBookMenu";
    }

    @GetMapping("/AddressBookMenu")
    public String menu(){
        return "AddressBookMenu";
    }

    @PostMapping("/Add")
    public String addPage(){
        return "redirect:/AddressBookAddBuddy";
    }

    @PostMapping("/View")
    public String viewPage(){
        return "redirect:/AddressBookView";
    }

    @PostMapping("/Remove")
    public String removePage(){
        return "redirect:/AddressBookRemove";
    }

    @PostMapping("/Back")
    public String Back(){
        return "redirect:/AddressBookMenu";
    }

    @GetMapping("/AddressBookAddBuddy")
    public String add(Model model){
        model.addAttribute("BuddyInfo",new BuddyInfo());
        return "AddressBookAddBuddy";
    }

    @PostMapping("/AddressBookAddBuddy")
    public String addbuddy(@ModelAttribute BuddyInfo buddyInfo, Model model, RedirectAttributes attributes){
        model.addAttribute("BuddyInfo", buddyInfo);
        createBuddy(buddyInfo.getName(),buddyInfo.getPhone_number());
        return "redirect:/AddressBookAddBuddy";
    }

    @GetMapping("/AddressBookView")
    public String greeting() {
        return "AddressBookView";
    }

    @GetMapping("/AddressBookRemove")
    public String remove() {
        return "AddressBookRemove";
    }

    @PostMapping("/RemoveBuddy")
    public String removeMethod(@RequestParam(name="id", required=false, defaultValue="World") String id, Model model){
        if(id == null){
            return "AddressBookRemove";
        }
        RemoveBuddy(id);
        return "redirect:/AddressBookRemove";
    }


    public void createBuddy(String name, String phone){
        try {
            URL url = new URL ("http://localhost:8080/rest/api/addressbooks/1/buddies");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            String jsonInputString = "{" + '"' + "name" + '"' + ":" + '"' + name + '"' + "," + '"' + "phone_number" + '"' + ":" + '"' + phone + '"'+"}";
            System.out.println(jsonInputString);
            try(OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
            }
        }
        catch (IOException e){
            System.out.println("Error");
        }
    }

    public void createAddress(){
        try {
            URL url = new URL ("http://localhost:8080/rest/api/addressbooks");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            String jsonInputString = "{" + '"' + "addressbookName" + '"' + ":" + '"' + "Buddies" + '"' + "}";
            try(OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
            }
        }
        catch (IOException e){
            System.out.println("Error");
        }
    }

    public void RemoveBuddy(String id){
        try {
            URL url = new URL ("http://localhost:8080/rest/api/buddies/"+id);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("DELETE");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
            }
        }
        catch (IOException e){
            System.out.println("Error");
        }
    }
}
