package com.lucie.bookbroker2.controllers;

import com.lucie.bookbroker2.models.Book;
import com.lucie.bookbroker2.models.LoginUser;
import com.lucie.bookbroker2.models.User;
import com.lucie.bookbroker2.services.BookService;
import com.lucie.bookbroker2.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @GetMapping("/")
    public String index(Model model) {
        // Bind empty User and LoginUser objects to the JSP to capture the form input
        model.addAttribute("newUser", new User());
        model.addAttribute("newLogin", new LoginUser());
        return "index.jsp";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("newUser") User newUser, BindingResult result, Model model, HttpSession session) {

        User user = userService.register(newUser, result);

        if(result.hasErrors()) {
            // Be sure to send in the empty LoginUser before re-rendering the page.
            model.addAttribute("newLogin", new LoginUser());
            return "index.jsp";
        }

        session.setAttribute("userId", user.getId());

        return "redirect:/home";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin, BindingResult result, Model model, HttpSession session) {

        User user = userService.login(newLogin, result);

        if(result.hasErrors() || user==null) {
            model.addAttribute("newUser", new User());
            return "index.jsp";
        }

        session.setAttribute("userId", user.getId());

        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        // Set userId to null and redirect to login/register page
        session.setAttribute("userId", null);

        return "redirect:/";
    }

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {

        Long userId = (Long) session.getAttribute("userId");
        if(userId == null) {
            return "redirect:/logout";
        }

        model.addAttribute("user", userService.findById(userId));

        List<Book> books = bookService.unborrowedBooks(userService.findById(userId));
        model.addAttribute("books", books);

        List<Book> myBooks = bookService.borrowedBooks(userService.findById(userId));
        model.addAttribute("myBooks", myBooks);

        return "home.jsp";
    }

    @GetMapping("/new/book")
    public String addBook(@ModelAttribute("book") Book book, HttpSession session) {

        if(session.getAttribute("userId") == null) {
            return "redirect:/logout";
        }

        return "newBook.jsp";
    }

    @PostMapping("/new/book")
    public String addBook(@Valid @ModelAttribute("book") Book book, BindingResult result, HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        if(userId == null) {
            return "redirect:/logout";
        }

        if(result.hasErrors()) {
            return "newBook.jsp";
        }else {
            bookService.addBook(new Book(book.getTitle(), book.getAuthor(), book.getThoughts(), userService.findById(userId)));
            return "redirect:/home";
        }
    }

    @GetMapping("/books/edit/{id}")
    public String edit(@PathVariable("id") Long id, HttpSession session, Model model) {

        if(session.getAttribute("userId") == null) {
            return "redirect:/logout";
        }

        Book book = bookService.findBook(id);
        model.addAttribute("book", book);
        return "updateBook.jsp";
    }

    @PutMapping("/books/edit/{id}")
    public String update(
            @PathVariable("id") Long id,
            @Valid @ModelAttribute("book") Book book,
            BindingResult result,
            HttpSession session) {

        if(session.getAttribute("userId") == null) {
            return "redirect:/logout";
        }

        if(result.hasErrors()) {
            return "updateBook.jsp";
        }else {
            bookService.updateBook(book);
            return "redirect:/home";
        }
    }

    @GetMapping("/books/{id}")
    public String showBooks(@PathVariable("id") Long id, HttpSession session, Model model) {

        Long userId = (Long) session.getAttribute("userId");
        if(userId == null) {
            return "redirect:/logout";
        }

        model.addAttribute("user", userService.findById(userId));

        Book book = bookService.findBook(id);
        model.addAttribute("book", book);

        return "showBook.jsp";
    }

    @RequestMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id, HttpSession session) {

        if(session.getAttribute("userId") == null) {
            return "redirect:/logout";
        }

        bookService.deleteBook(bookService.findBook(id));

        return "redirect:/home";
    }

}
