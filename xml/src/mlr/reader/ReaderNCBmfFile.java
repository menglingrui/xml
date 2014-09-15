package mlr.reader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class ReaderNCBmfFile {
	public static void main(String[] args){
	//	read();
	}
	
	public  static String read(String panth){  
	    try {  
	    	StringBuffer buf=new StringBuffer();
	    	SAXReader read=new SAXReader();	    	
	        Document doc = read.read(new File(panth));  
	        List<Element> itemList =  (List<Element>) doc.selectNodes("/component/celllist/entity/attributelist");  
	        
	        Map<String,String> attmap=new HashMap<String,String>();
	        
	        for (Iterator<Element> iter = itemList.iterator(); iter.hasNext();){  
	            Element element = iter.next();  
	            Element parent= element.getParent();
	            Attribute paatt= parent.attribute("displayName");	            
	            System.out.println("类名字为："+paatt.getText());
	            buf.append("类名字为："+paatt.getText()+"\n");
	            Iterator<Element> it= element.elementIterator();
	            
	            if(it !=null ){
	            	while(it.hasNext()){
	            		Element el=it.next();
	            		
	            		String id=el.attribute("id").getText();
	            		
	            		String code=el.attribute("fieldName").getText();
	            		
	            		if(attmap.get(id)==null){
	            			attmap.put(id, code);
	            		}else{
	            			System.out.println("重复的属性id为："+id+"");
	            			buf.append("重复的属性id为："+id+"名称为："+code+"\n");
	            		}
	            		
	            	}
	            	
	            }	            
	        } 
	        return buf.toString();
	    } catch (DocumentException e) {  
	        e.printStackTrace();  
	    }
		return "";  
	} 
	
	public  static  Map<String,String> readAndReturnDatas(String panth){  
	    try {  
	    	StringBuffer buf=new StringBuffer();
	    	SAXReader read=new SAXReader();	    	
	        Document doc = read.read(new File(panth));  
	        List<Element> itemList =  (List<Element>) doc.selectNodes("/component/celllist/entity/attributelist");  
	        
	        Map<String,String> attmap=new HashMap<String,String>();
	        
	        Map<String,String> reAttrMap=new HashMap<String,String>();
	        
	        for (Iterator<Element> iter = itemList.iterator(); iter.hasNext();){  
	            Element element = iter.next();  
	            Element parent= element.getParent();
	            Attribute paatt= parent.attribute("displayName");	            
	            System.out.println("类名字为："+paatt.getText());
	            buf.append("类名字为："+paatt.getText()+"\n");
	            Iterator<Element> it= element.elementIterator();
	            
	            if(it !=null ){
	            	while(it.hasNext()){
	            		Element el=it.next();
	            		
	            		String id=el.attribute("id").getText();
	            		
	            		String code=el.attribute("fieldName").getText();
	            		
	            		if(attmap.get(id)==null){
	            			attmap.put(id, code);
	            		}else{
	            			System.out.println("重复的属性id为："+id+"");
	            			buf.append("重复的属性id为："+id+"\n");	            			
	            			reAttrMap.put(id, id);
	            		}
	            		
	            	}
	            	
	            }	            
	        } 
	        return reAttrMap;
	    } catch (DocumentException e) {  
	        e.printStackTrace();  
	    }
		return new HashMap<String,String>();  
	}

	public static String reOutUUIDS(Map<String, String> map,String path) {
		if(map==null || map.size()==0){
			return null;
		}
		
		if(path==null|| path.length()==0){
			return null;
		}
		
	 	StringBuffer buf=new StringBuffer();
    	SAXReader read=new SAXReader();	    	
        Document doc;
		try {
			doc = read.read(new File(path));
		 
        List<Element> itemList =  (List<Element>) doc.selectNodes("/component/celllist/entity/attributelist");  
        
        
        
        for (Iterator<Element> iter = itemList.iterator(); iter.hasNext();){  
            Element element = iter.next();  
            Element parent= element.getParent();
            Attribute paatt= parent.attribute("displayName");	            
            System.out.println("类名字为："+paatt.getText());
            buf.append("类名字为："+paatt.getText()+"\n");
            Iterator<Element> it= element.elementIterator();
            
            if(it !=null ){
            	while(it.hasNext()){
            		Element el=it.next();
            		
            		String id=el.attribute("id").getText();
            		
            		
            		if(map.get(id)!=null&&map.get(id).equals(id)){
            			UUID uuid= UUID.randomUUID();
            			
            		    el.attribute("id").setText(uuid.toString());
            		}
            			
            		
            		
            	}
            	
            }	            
        }
		buf.append("修复成功");
		
		writeToFile(doc, path, doc.getXMLEncoding());
		
		return buf.toString();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return ""; 
	} 
    public static void writeToFile(Document doc, String filePath,
            String encoding) {
        try {
            OutputFormat fmt = OutputFormat.createPrettyPrint();
            fmt.setEncoding(encoding);

            XMLWriter xmlWriter = new XMLWriter(new OutputStreamWriter(
                    new FileOutputStream(filePath), encoding), fmt);
            xmlWriter.write(doc);
            xmlWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
