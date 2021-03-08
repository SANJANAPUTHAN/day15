package utilities;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Invoice {
	private int item_id;
	private String item_name;
	private int quantity;
	private float price;
	private String brand;	
	public Invoice(int item_id, String item_name, int quantity, float price, String brand) {
		super();
		this.item_id = item_id;
		this.item_name = item_name;
		this.quantity = quantity;
		this.price = price;
		this.brand = brand;
	}	
	public final int getItem_id() {
		return item_id;
	}
	public final void setItem_id(int item_id) {
		
		this.item_id = item_id;
	}
	public final String getItem_name() {
		return item_name;
	}
	public final void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	
	public final void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public final int getQuantity() {
		return quantity;
	}
	public final float getPrice() {
		return price;
	}
	public final void setPrice(float price) {
		this.price = price;
	}
	public final String getBrand() {
		return brand;
	}
	public final void setBrand(String brand) {
		this.brand = brand;
	}
	@Override
	public String toString() {
		return item_id+" "+item_name+" "+quantity+" "+price+" "+brand;
	}
	public Invoice() {
	}
	public static List<Invoice> getInvoices() throws Exception {
		DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
		dbf.setValidating(true);
		dbf.setIgnoringElementContentWhitespace(true);
		DocumentBuilder db=dbf.newDocumentBuilder();
		Document doc=db.parse("products.xml");
		System.out.println(doc.getDocumentElement().getNodeName());
		NodeList nodelist=doc.getElementsByTagName("item");
		int length=nodelist.getLength();
		List<Invoice> list=new ArrayList<Invoice>();
		for(int i=0;i<length;i++)
		{			
			list.add(getInvoice(nodelist.item(i)));
		}
		return list;
		
	}
	private static Invoice getInvoice(Node item) {
		Invoice invoice =new Invoice();
		if(item.getNodeType()==Node.ELEMENT_NODE)
		{
			 Element element = (Element)item;
			 invoice.setItem_id(Integer.parseInt(getTagValue("item_id", element)));
			 invoice.setItem_name(getTagValue("item_name", element));
			 invoice.setQuantity(Integer.parseInt(getTagValue("quantity", element)));
			 invoice.setPrice(Float.parseFloat(getTagValue("price", element)));
			 invoice.setBrand(getTagValue("brand", element));
			 
		}
		return invoice;
	}
	private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }
}
