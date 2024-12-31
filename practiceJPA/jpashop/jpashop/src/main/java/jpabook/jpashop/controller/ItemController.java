package jpabook.jpashop.controller;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.dto.BookForm;
import jpabook.jpashop.domain.entity.item.Book;
import jpabook.jpashop.domain.entity.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/new")
    public String createForm(Model model){
        model.addAttribute("form",new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/new")
    public String create(@Valid BookForm bookForm){
        Book item = new Book();
        item.updateBook(bookForm.getName(), bookForm.getPrice(), bookForm.getStockQuantity(), bookForm.getAuthor(), bookForm.getIsbn());

        itemService.saveItem(item);
        return "redirect:/";
    }

    @GetMapping("")
    public String list(Model model){
        List<Item> items = itemService.findItems();
        model.addAttribute("items",items);
        return "items/itemList";
    }

    @GetMapping("{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId")Long itemId, Model model){
        BookForm bookForm = new BookForm();
        Book book = (Book) itemService.findOne(itemId);
        bookForm.setId(book.getId());
        bookForm.setIsbn(book.getIsbn());
        bookForm.setName(book.getName());
        bookForm.setPrice(book.getPrice());
        bookForm.setAuthor(book.getAuthor());
        bookForm.setStockQuantity(book.getStockQuantity());

        model.addAttribute("form",bookForm);
        return "items/updateItemForm";
    }

    @PostMapping("{itemId}/edit")
    public String updateItem(@PathVariable("itemId")Long itemId, Model model, BookForm bookForm){
        Book book = (Book) itemService.findOne(itemId);
        book.updateBook(bookForm.getName(), bookForm.getPrice(), bookForm.getStockQuantity(), bookForm.getAuthor(), bookForm.getIsbn());
        itemService.saveItem(book);
        return "redirect:/items";
    }

}
