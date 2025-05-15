import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Product book1 = new Product(101L, "AAAAAA", "book", 109.99);
        Product book2 = new Product(102L, "bbbb", "book", 99.99);
        Product book3 = new Product(103L, "ccc", "book", 101.00);
        Product baby1 = new Product(104L, "BabyClothes", "Baby", 50.00);
        Product baby2 = new Product(105L, "BabyFood", "Baby", 20.00);
        Product boys1 = new Product(106L, "Boys Hat", "Boys", 25.00);
        Product boys2 = new Product(107L, "Boys Sneakers", "Boys", 129.00);
        List<Product> prodotti = List.of(book1, book2, book3, boys1, boys2, baby1, baby2);
        List<Double> l1 = prodotti.stream().map(product -> product.getPrice()).collect(Collectors.toList());

        Customer lucio = new Customer(1L, "Lucio", 1);
        Customer floria = new Customer(2L, "Floria", 2);
        Customer lello = new Customer(3L, "Lello", 3);

        List<Product> allProducts = List.of(book1, book2, book3, baby1, baby2, boys1, boys2);

        List<Product> result1 = allProducts.stream().filter(product -> "book".equals(product.getCategory()) && product.getPrice() > 100)
                .toList();
        System.out.println("gli articoli book con prezzo superiore a 100 sono: " + result1);
        System.out.println();
        Order order1 = new Order(101L, "Delivered", LocalDate.of(2025, 05, 13), LocalDate.of(2025, 05, 14), List.of(baby1, baby2), floria);
        Order order2 = new Order(102L, "Delivered", LocalDate.of(2021, 03, 10), LocalDate.of(2021, 03, 25), List.of(boys1, boys2), lucio);
        List<Order> allOrders = List.of(order1, order2);


        List<Order> result2 = allOrders.stream().filter(order -> order.getProducts().stream().anyMatch(product -> "Baby".equals(product.getCategory()))).toList();
        System.out.println("i prodotti con categoria Baby sono: " + result2);
        System.out.println();

        List<Product> result3 = allProducts.stream().filter(product -> "Boys".equals(product.getCategory())).map(product -> new Product(product.getId(), product.getName(), product.getCategory(), product.getPrice() * 0.9)).toList();
        System.out.println("applico lo sconto del 10% ai prodotti Boys: " + result3);


        System.out.println("---------Esercizio1----------");
        Map<Customer, List<Order>> orderByCustomer = allOrders.stream().collect(Collectors.groupingBy(Order::getCustomer));
        System.out.println(orderByCustomer);
        System.out.println();
        System.out.println("-------Esercizio2-------");
        ;
        Map<Customer, Double> totalEachCustomer = allOrders.stream().collect(Collectors.groupingBy(Order::getCustomer, Collectors.summingDouble(order -> order.getProducts().stream().mapToDouble(Product::getPrice).sum())));
        System.out.println(totalEachCustomer);
        System.out.println();
        System.out.println("----------Esercizio3----------");
        DoubleSummaryStatistics stats = prodotti.stream().collect(Collectors.summarizingDouble(Product::getPrice));
        double maxPrice = stats.getMax();
        System.out.println(maxPrice);
        System.out.println();
        System.out.println("--------Esercizio4---------");
        DoubleSummaryStatistics orderStats = allOrders.stream().collect(Collectors.summarizingDouble(order -> order.getProducts().stream().mapToDouble(Product::getPrice).sum()));
        double averageOrderAmount = orderStats.getAverage();
        System.out.println(averageOrderAmount);
        System.out.println();
        System.out.println("------------Esercizio5-----------");
        Map<String, Double> totalByCategory = prodotti.stream().collect(Collectors.groupingBy(Product::getCategory, Collectors.summingDouble(Product::getPrice)));
        System.out.println(totalByCategory);
        System.out.println();
        System.out.println("-----------Esercizio6---------");
        salvaProdottiSuDisco(prodotti, "prodotti.txt");
    }

    public static void salvaProdottiSuDisco(List<Product> prodotti, String percorso) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < prodotti.size(); i++) {
            Product p = prodotti.get(i);
            sb.append(p.getName()).append("@").append(p.getCategory()).append("@").append(p.getPrice());
            if (i < prodotti.size() - 1) {
                sb.append("#");
            }
            try {
                FileUtils.writeStringToFile(new File(percorso), sb.toString(), "UTF-8");
                System.out.println("File salvato in: " + percorso);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
