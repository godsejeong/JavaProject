import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Main extends JFrame{
	ArrayList dongList = new ArrayList<String>();
	String json;
	String selectdata;
	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();
	JPanel p3 = new JPanel();
	JLabel lable = new JLabel("");
	JComboBox dong = new JComboBox();
	
	public Main() {
		String[] locationList = {"서울","부산","대구","인천","광주","대전","울산","경기","강원","충북","충남","전북","전남","경북","경남","제주","세종"};
		JComboBox location = new JComboBox(locationList);
		JButton button = new JButton("검색");
		
		setTitle("날씨");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		JPanel f1 = new JPanel();
		f1.setBackground(Color.gray);
		JPanel f2 = new JPanel();
		f2.setBackground(Color.white);
		JPanel art = new JPanel();
		
		c.add(f1, BorderLayout.NORTH);
		c.add(f2, BorderLayout.CENTER);
		
		f1.add(p1);
		f1.add(p2);
		f1.add(p3);
		p1.add(new JLabel("시를 선택해주세요"));
		p1.add(location);
		
		p2.add(new JLabel("동을 선택해주세요"));
		p2.add(dong);
		p3.add(button);
		
		p3.setVisible(false);
		p2.setVisible(false);
		
		location.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 JComboBox cb = (JComboBox) e.getSource();
				 int index = cb.getSelectedIndex();
				 apiexplorer(locationList[index]);

				 p2.setVisible(true);
			}
		});

		dong.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 JComboBox cb = (JComboBox) e.getSource();
				 int index = cb.getSelectedIndex();
				 selectdata = dongList.get(index).toString();
				 p3.setVisible(true);
			}
		});
		
		button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	finedustInfo();
            }
        });
		
		f2.add(lable);
		
		setSize(600,300);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Main();
	}
	
	public void apiexplorer(String location) {
		dongList.clear();
		 FinedustExplorer explorer = new FinedustExplorer(location);
		 explorer.start();
		 try {
		 explorer.join();
		 }catch (Exception e) {
			e.printStackTrace();
		}
		 System.out.println(explorer.returndata());
		 json = explorer.returndata();
		 try {
		 JSONObject obj =(JSONObject) new JSONParser().parse(json);
		 JSONArray array = (JSONArray) obj.get("list");
		 for(int i =0;i<array.size();i++) {
			 JSONObject tmp = (JSONObject) array.get(i);
			 System.out.println(tmp.get("cityName"));
			 dongList.add(tmp.get("cityName"));
		 }
		 
		 dong.setModel(new JComboBox<>(dongList.toArray()).getModel());
		 System.out.println(dongList.size());
		 }catch (Exception e) {
			 e.printStackTrace();
		 }
	}
	
	public void finedustInfo() {
		try {
		JSONObject obj =(JSONObject) new JSONParser().parse(json);
		JSONArray array = (JSONArray) obj.get("list");
		 for(int i =0;i<array.size();i++) {
			 JSONObject tmp = (JSONObject) array.get(i);
			 
			 if(tmp.get("cityName").equals(selectdata)) {
				 lable.setText("미세먼지 농도 : " + tmp.get("pm25Value"));
			 }
		 }
		}catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}