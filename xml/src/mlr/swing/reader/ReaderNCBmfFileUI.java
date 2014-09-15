package mlr.swing.reader;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;

import mlr.reader.ReaderNCBmfFile;

public class ReaderNCBmfFileUI extends JFrame implements ActionListener{
	private JLabel tile = new JLabel("�鿴����Bmf�ļ�ʵ������ID�ظ�");

	private JLabel jcooll = new JLabel("ѡ��bmf�ļ�: ");

    private String path=null;
    
    private String name=null;

	private JTextArea ja = new JTextArea();
	
	private JButton but=new JButton("���");
	
	private JButton but1=new JButton("��������ʵ�����ظ���UUID");

	public ReaderNCBmfFileUI() {
		super();
		init();
		initLis();
	}

	public void initLis() {
       
		but.addActionListener( this);
		
		but1.addActionListener( this);
	}

	public void init() {
		JPanel zpanle=new JPanel();
		
		zpanle.setLayout(new BorderLayout());
		
		
		
        JPanel tpanel=new JPanel();
        
        tpanel.setLayout(new FlowLayout());
        
        tpanel.add(tile);
        
        zpanle.add(tpanel,BorderLayout.NORTH);
        
        JPanel zcpanel=new JPanel();
        zcpanel.setLayout(new BorderLayout());
        
        
        JPanel cpanel=new JPanel();
        
        cpanel.setLayout(new FlowLayout());
        
        cpanel.add(jcooll);
        cpanel.add(but);
        cpanel.add(but1);
        
    //    cpanel.add(jcool);
        
        JPanel cpanel1=new JPanel();
        
        cpanel1.setLayout(new BorderLayout());
        
        cpanel1.add(ja,BorderLayout.CENTER);
        
        zcpanel.add(cpanel,BorderLayout.NORTH);
        
        zcpanel.add(cpanel1,BorderLayout.CENTER);
        
        zpanle.add(zcpanel,BorderLayout.CENTER);
        
        
       // zpanle.add(but,BorderLayout.SOUTH);
        
     
        
        this.setContentPane(zpanle);
        
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==but){
			 JFileChooser jcool = new JFileChooser();
			 int i = jcool.showOpenDialog(null);
			 
			 int x=0;
		     if(i== jcool.APPROVE_OPTION){ //���ļ�
		    	 jcool.setFileFilter(new FileFilter() {
						@Override
						public String getDescription() {
							return null;
						}
						
						@Override
						public boolean accept(File file) {	
							if(file.getName().endsWith(".bmf")){
								return true;
							}
							return false;
						}
					});
		            path = jcool.getSelectedFile().getAbsolutePath();
		            name = jcool.getSelectedFile().getName();
		            System.out.println("��ǰ�ļ�·����"+path+";\n��ǰ�ļ�����"+name);	            
		            if(!name.endsWith(".bmf")){
		            	ja.setText("��ѡ������Bmf�ļ�");
		            }else{
		            	ja.setText(ReaderNCBmfFile.read(path));
		            }
		            
		     }
		}
		 if(e.getSource()==but1){
	    	 if(path==null || path.length()==0 || name==null || name.length()==0){
	    		 ja.setText("�������");
	    		 return;
	    	 }
	    	 
	    	 if(!name.endsWith(".bmf")){
	    		 ja.setText("����ķ�����bmf�ļ�");
	    	 }
	    	 
	    	Map<String,String> map= ReaderNCBmfFile.readAndReturnDatas(path);
	    	
	    	if(map.size()==0){
	    		ja.setText("û���ظ���ʵ������");
	    	}
	    	
	    	ja.setText(ReaderNCBmfFile.reOutUUIDS(map,path));
	         
	     }
		
	}
	
	public static void main(String[] args){
		ReaderNCBmfFileUI ui=new ReaderNCBmfFileUI();
		ui.setSize(600,500);
		ui.setVisible(true);
	}
	

}
