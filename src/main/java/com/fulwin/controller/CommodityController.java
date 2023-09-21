package com.fulwin.controller;

import com.fulwin.pojo.Commodity;
import com.fulwin.pojo.Cusinfo;
import com.fulwin.pojo.Customer;
import com.fulwin.service.CommodityService;
import com.fulwin.service.CusinfoService;
import com.fulwin.service.CustomerService;
import com.fulwin.util.Image;
import com.fulwin.util.ListAndString;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import static com.fulwin.util.Image.splitImagesAndToBase64;

@Controller
@RequestMapping("/shop")
public class CommodityController {

    @Autowired
    private CommodityService commodityService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CusinfoService cusinfoService;

    @GetMapping("/item/{id}")
    public String singleItem(@PathVariable Long id, Model model){

        Commodity commodity = commodityService.getCommodityById(id);
        byte[] delimiter = new byte[] { (byte) 0x1A, (byte) 0x2B, (byte) 0x3C, (byte) 0x4D, (byte) 0x5E, (byte) 0x6F };

        String profilepic = Base64.getEncoder().encodeToString(customerService.getCustomerById(commodity.getItemCusid()).getProfilePicture());
        String pictureBase64 = Base64.getEncoder().encodeToString(commodity.getItemPicture());
        model.addAttribute("picture", pictureBase64);
        model.addAttribute("username", customerService.getCustomerById(commodity.getItemCusid()).getUsername());
        model.addAttribute("price", commodity.getItemPrice());
        model.addAttribute("intro", commodity.getItemIntro());
        model.addAttribute("name", commodity.getItemName());
        model.addAttribute("cuspic", profilepic);


        if(commodity.getItemBpicture() != null) {
            byte[] itemBpicture = commodity.getItemBpicture();
            model.addAttribute("bpictures", splitImagesAndToBase64(itemBpicture));
        }else {
            model.addAttribute("bpictures", null);
        }

        model.addAttribute("info", cusinfoService.getCusinfoById(commodity.getItemCusid()));

        return "shop/single-product";
    }

    @GetMapping("/cart")
    public String cart(Model model, HttpSession session){
        String email = (String) session.getAttribute("email");
        List<Customer> customerByEmail = customerService.getCustomerByEmail(email);
        Customer customer = customerByEmail.get(0);
        List<Long> itemIds = ListAndString.convertCartStringToLongList(customer.getCart());
        List<Commodity> items = new ArrayList<>();
        List<String> pictures = new ArrayList<>();
        BigDecimal totalMoney = new BigDecimal("0");

        for (Long itemId : itemIds) {
            Commodity commodityById = commodityService.getCommodityById(itemId);
            String picture = "";
            if (commodityById.getItemPicture() != null) {
                picture = Base64.getEncoder().encodeToString(commodityById.getItemPicture());
            }
            pictures.add(picture);
            totalMoney = totalMoney.add(commodityById.getItemPrice());
            items.add(commodityById);
        }

        model.addAttribute("items", items);
        model.addAttribute("subtotal", totalMoney.setScale(2, RoundingMode.HALF_UP));
        model.addAttribute("fee", totalMoney.multiply(new BigDecimal("0.037")).setScale(2, RoundingMode.HALF_UP));
        model.addAttribute("total", totalMoney.add(totalMoney.multiply(new BigDecimal("0.037"))).setScale(2, RoundingMode.HALF_UP));
        model.addAttribute("userid", customer.getId());
        model.addAttribute("pictures", pictures);

        return "shop/cart-page";
    }

    @GetMapping("/deletecartitem/{id}")
    public String deleteCartItem(@PathVariable Long id, HttpSession session) {
        String email = (String) session.getAttribute("email");
        List<Customer> customerByEmail = customerService.getCustomerByEmail(email);
        Customer customer = customerByEmail.get(0);
        customerService.deleteCommodityFromCartByUserId(customer.getId(), id);
        return "redirect:/shop/cart"; // Redirect to /shop/cart
    }

    @GetMapping("/deleteitem/{id}")
    public String deleteItem(@PathVariable Long id, Model model) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        List<Customer> customers = customerService.getCustomerByEmail((String) session.getAttribute("email"));
        Customer customer = customers.get(0);

        Commodity commodityById = commodityService.getCommodityById(id);

        if (customer.getId().equals(commodityById.getItemCusid())) {
            commodityService.deleteCommodityById(id);
            model.addAttribute("success", "Item deleted successfully.");
        }
        else
            model.addAttribute("error", "you can't delete other's item");

        return "redirect:/dashboard/profile";
    }

    @GetMapping("/addcommodity")
    public String addItemPage(Model model){

        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        List<Customer> customers = customerService.getCustomerByEmail((String) session.getAttribute("email"));
        Customer customer = customers.get(0);

        model.addAttribute("name", customer.getUsername());
        model.addAttribute("balance", customer.getBalance());
        model.addAttribute("info", cusinfoService.getCusinfoById(customer.getId()));

        return "dashboard/user-add-item";
    }

    @PostMapping("/addcommodity")
    public String addItem(@RequestParam("group") String category,
                          @RequestParam("name") String productName,
                          @RequestParam("intro") String productDescription,
                          @RequestParam("price") BigDecimal regularPrice,
                          @RequestParam(value = "offerPrice", required = false) BigDecimal offerPrice,
                          @RequestPart("cover") MultipartFile coverImage,
                          @RequestPart(value = "firpic", required = false) MultipartFile firstImage,
                          @RequestPart(value = "secpic", required = false) MultipartFile secondImage,
//                          @RequestPart(value = "thipic", required = false) MultipartFile thirdImage,
                          Model model) throws IOException {


        // Convert MultipartFile to byte[]
        byte[] coverImageBytes = coverImage.getBytes();
        byte[] firstImageBytes = (firstImage != null) ? firstImage.getBytes() : new byte[0];
        byte[] secondImageBytes = (secondImage != null) ? secondImage.getBytes() : new byte[0];

        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        List<Customer> customers = customerService.getCustomerByEmail((String) session.getAttribute("email"));
        Customer customer = customers.get(0);

        Commodity commodity = new Commodity();
        commodity.setItemCusid(customer.getId());
        commodity.setItemPrice(regularPrice);
        commodity.setItemName(productName);
        commodity.setItemGroup(category);
        commodity.setItemIntro(productDescription);
        commodity.setItemPicture(coverImageBytes);

        List<byte[]> images = new ArrayList<>();

        images.add(firstImageBytes);
        images.add(secondImageBytes);
//        images.add(thirdImageBytes);

        if (images.stream().anyMatch(arr -> arr.length > 0)) {
            commodity.setItemBpicture(Image.concatenateImagesWithDelimiter(images));
        }

        commodityService.insertCommodity(commodity);

        return "redirect:/shop/addcommodity?success=Success Added!";
    }

    @GetMapping("/editcommodity/{id}")
    public String editItemPage(@PathVariable("id") Long id, Model model){

        //check does user has this model
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        List<Customer> customers = customerService.getCustomerByEmail((String) session.getAttribute("email"));
        Customer customer = customers.get(0);

        //check does user has this commodity
        if(commodityService.getCommodityById(id).getItemCusid().equals(customer.getId())){
            model.addAttribute("name", customer.getUsername());
            model.addAttribute("balance", customer.getBalance());
            model.addAttribute("info", cusinfoService.getCusinfoById(customer.getId()));
            return "dashboard/user-edit-item";
        }
        else
            return "error/500";

    }




}

