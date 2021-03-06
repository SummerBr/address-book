import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;
import static java.lang.System.out;
import java.lang.*;

import java.util.Map;
import java.util.HashMap;

import spark.ModelAndView;

import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App{
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      model.put("contactList", AddressBook.all());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/contacts", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      ArrayList<AddressBook> contactList = AddressBook.all();

      String firstName = request.queryParams("firstName");
      String lastName = request.queryParams("lastName");
      AddressBook newAddressBook = new AddressBook(firstName, lastName);
      //contactList.add(newAddressBook);
      newAddressBook.save();
      model.put("contactList", contactList);
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/contact-form/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      AddressBook contact = AddressBook.find(Integer.parseInt(request.params(":id")));
      model.put("contact", contact);
      model.put("template", "templates/contact-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
