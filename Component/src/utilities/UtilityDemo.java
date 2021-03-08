package utilities;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class UtilityDemo {
public static void main(String[] args) throws Exception {
	Object obj=new UtilityDemo();	
	obj=Proxy.newProxyInstance(UtilityDemo.class.getClassLoader(), new Class[] {ConversionComponent.class}, new MyInvocationHandler(new Object[] {new XMLToExcel()}));
	ConversionComponent xml=(ConversionComponent)obj;
	xml.convert();
}
}
interface ConversionComponent
{
	public void convert() throws Exception;
}
class XMLToExcel implements ConversionComponent
{

	@Override
	public void convert() throws Exception 
	{
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet spreadsheet = workbook.createSheet("Department List");
		XSSFRow row;
		List<Invoice> list=Invoice.getInvoices();
		ListIterator<Invoice> l=list.listIterator();
		Map < Integer,String[] > prodinfo = new TreeMap <Integer, String[] >();
		int i=1;		
		while(l.hasNext())
		{
			prodinfo.put(i++,l.next().toString().split(" "));
		}
		Row header = spreadsheet.createRow(0);
		
	    header.createCell(0).setCellValue("ITEM_ID");
	    header.createCell(1).setCellValue("ITEM_NAME");
	    header.createCell(2).setCellValue("QUANTITY");
	    header.createCell(3).setCellValue("PRICE");
	    header.createCell(4).setCellValue("BRAND");
	    	CellStyle style=null;
	        XSSFFont font= workbook.createFont();
	        font.setFontHeightInPoints((short)10);
	        font.setFontName("Arial");
	        font.setColor(IndexedColors.BLACK.getIndex());
	        font.setBold(true);
	        font.setItalic(false);
	 
	        style=workbook.createCellStyle();
	        style.setFont(font);
	        header.setRowStyle(style);
		Set<Integer> keyid = prodinfo.keySet();
	    int rowid = 1;
	    for (Integer key : keyid) {
	         row = spreadsheet.createRow(rowid++);
	         String [] objectArr = prodinfo.get(key);
             int cellid = 0;
	         
	         for (Object obj : objectArr){
	            Cell cell = row.createCell(cellid++);
	            cell.setCellValue((String)obj);
	         }
	    }
	    FileOutputStream out = new FileOutputStream(new File("C:/temp1/department.xlsx"));
	    workbook.write(out);
	    out.close();
	}
}
class MyInvocationHandler implements InvocationHandler
{
	Object ob[];

	MyInvocationHandler(Object ob[])
	{
		this.ob=ob;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object role=null;
		for(Object o:ob)
		{
			Method m[]=o.getClass().getDeclaredMethods();
			for(Method mm:m)
			{
				mm.setAccessible(true);
				role=mm.invoke(o, args);
			}
		}
		return role;
	}
}
