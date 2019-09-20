package entities;

import entities.Customer;
import entities.OrderLine;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-09-20T14:27:58")
@StaticMetamodel(Order.class)
public class Order_ { 

    public static volatile ListAttribute<Order, OrderLine> orderLine;
    public static volatile SingularAttribute<Order, Integer> id;
    public static volatile SingularAttribute<Order, Integer> OrderID;
    public static volatile SingularAttribute<Order, Customer> customer;

}