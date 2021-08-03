package com.example.geekshoppinglist.controllers;

import com.example.geekshoppinglist.entity.ShoppingItem;
import com.example.geekshoppinglist.entity.User;
import com.example.geekshoppinglist.repository.ShoppingItemRepository;
import com.example.geekshoppinglist.repository.UserRepostory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class ShoppingListController {

    private static final Logger logger = LoggerFactory.getLogger(ShoppingListController.class);

    private final ShoppingItemRepository repository;

    private final UserRepostory userRepostory;

    @Autowired
    public ShoppingListController(ShoppingItemRepository repository, UserRepostory userRepostory) {
        this.repository = repository;
        this.userRepostory = userRepostory;
    }

    @GetMapping("/")
    public String indexPage(Model model, Principal principal){
        logger.info("Username: {}", principal.getName());

        model.addAttribute("items", repository.findByUserUsername(principal.getName()));
        model.addAttribute("item", new ShoppingItem());
        return "index";
    }

    @PostMapping("/")
    public String newShoppingItem(ShoppingItem shoppingItem, Principal principal){
        logger.info("Username: {}", principal.getName());

        User user = userRepostory.findByUsername(principal.getName()).get();
        shoppingItem.setUser(user);
        repository.save(shoppingItem);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String deleteShopppingItem(@PathVariable("id") Long id){
        repository.deleteById(id);
        return "redirect:/";
    }

}
