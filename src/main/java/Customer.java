import java.util.Objects;

public class Customer {

        private Long id;
        private String name;
        private Integer tier;

        public Customer(Long id, String name, Integer tier) {
            this.id = id;
            this.name = name;
            this.tier = tier;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Integer getTier() {
            return tier;
        }

        @Override
        public String toString() {
            return "Customer{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", tier=" + tier +
                    '}';
        }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Customer customer)) return false;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}


