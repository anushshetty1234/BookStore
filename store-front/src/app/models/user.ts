import { Order } from "./order";
import { ShoppingCart } from "./shopping-cart";
import { UserPayment } from "./user-payment";
import { UserShipping } from "./user-shipping";


export class User {
	public id: number;
	public firstName: string;
	public lastName: string;
	public username: string;
	public password: string;
	public email: string
	public phone: string;
	public enabled: boolean;
	public userPaymentList: UserPayment[];
	public userShippingList: UserShipping[];
	public shoppingCart: ShoppingCart;
	public orderList: Order[];
}

