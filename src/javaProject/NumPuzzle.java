package javaProject;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;

public class NumPuzzle {
	public static void main(String[] args) {
		GameFrame gf = new GameFrame();
	}
}

class GameFrame extends JFrame {
	Font font;
	JPanel gamePanel;
	JButton[][] numBtn; //�����ǿ� ������ JButton �迭

	JButton upBtn = new JButton("UP");
	JButton downBtn = new JButton("DOWN");
	JButton leftBtn = new JButton("LEFT");
	JButton rightBtn = new JButton("RIGHT");

	Random ranPlace = new Random();

	int randNum1;
	int randNum2;
	int randNum3;
	int randNum4;

	int arr[][] = new int [4][4]; //btnNum[i][j]�迭�� ȣȯ�� integer �迭 ����


	public GameFrame() {
		JFrame frame = new JFrame("1024 GAME");
		frame.setPreferredSize(new Dimension(600, 800));
		Container contentPane = frame.getContentPane();

		//����Ÿ��Ʋ GUI
		JPanel panel1 = new JPanel();
		JLabel labelTitle = new JLabel("1024 NumPuzzleGAME");
		font = new Font("SanSerif", Font.BOLD, 30);
		labelTitle.setFont(font);
		labelTitle.setForeground(Color.BLUE);
		panel1.add(labelTitle);

		JPanel panel2 = new JPanel();
		JButton restartBtn = new JButton("Restart");

		restartBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < 4; i++) {
					for (int j = 0; j < 4; j++) {
						arr[i][j] = 0;  //�迭 ����
						numBtn[i][j].setText(""); //������ ����
						numBtn[i][j].setBackground(null); //��� ����
					}
				}
			}
		});
		panel2.add(restartBtn);

		//�ʱ� ���� ���� ���� ��ư
		JButton initializeBtn = new JButton("Initialize");
		initializeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == initializeBtn){

					//��ư ���� ������ �ʱ��ư �ٽ� ������ �� �ֵ��� ó���� 0���� ����
					arr[randNum1][randNum2] = 0;
					numBtn[randNum1][randNum2].setText("");
					numBtn[randNum1][randNum2].setBackground(null);

					arr[randNum3][randNum4] = 0;
					numBtn[randNum3][randNum4].setText("");
					numBtn[randNum3][randNum4].setBackground(null);

					randNum1 = ranPlace.nextInt(4);
					randNum2 = ranPlace.nextInt(4);
					randNum3 = ranPlace.nextInt(4);
					randNum4 = ranPlace.nextInt(4);

					//���� 2 ������ġ ����
					arr[randNum1][randNum2] = 2;
					numBtn[randNum1][randNum2].setText("2");

					//���� 4 ������ġ ����
					arr[randNum3][randNum4] = 4;
					numBtn[randNum3][randNum4].setText("4");
					numBtn[randNum3][randNum4].setBackground(Color.yellow);
				}
			}
		});

		panel2.add(initializeBtn);


		contentPane.add(panel1, BorderLayout.NORTH);
		panel1.add(panel2);

		gamePanel = new JPanel();
		gamePanel.setLayout(new GridLayout(4, 4));


		numBtn = new JButton[4][4]; //4 X 4 �迭�� JButton�迭����

		//gamePanel�� JButton �迭 ���̱�
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				numBtn[i][j] = new JButton("");
				numBtn[i][j].setFont(new Font("���� ���", Font.BOLD, 50)); 
				gamePanel.add(numBtn[i][j]);
			}
		}

		contentPane.add(gamePanel, BorderLayout.CENTER);

		//���Ӽ����� �󺧻���
		JPanel helpPanel = new JPanel(new GridLayout(3, 1));
		JLabel helpLabel = new JLabel("       Click to move. (ex) 2 + 2 = 4. Reach 1024. Good Luck!");
		font = new Font("SanSerif", Font.BOLD, 20);
		helpLabel.setFont(font);
		helpLabel.setForeground(Color.RED);
		helpPanel.add(helpLabel);

		JPanel controlPanel = new JPanel();

		//�̸� �� ���� ���� ���� 2�� 4 ���
		randNum1 = ranPlace.nextInt(4);
		randNum2 = ranPlace.nextInt(4);
		randNum3 = ranPlace.nextInt(4);
		randNum4 = ranPlace.nextInt(4);

		numBtn[randNum1][randNum2].setText("2");
		numBtn[randNum3][randNum4].setText("4");
		numBtn[randNum3][randNum4].setBackground(Color.yellow);

		//4�� ���� ������ ActionListener
		leftBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {                                                             
				constructRandNum(); //���������޼ҵ�
				leftSort(); //�������� ���ڸ� �����ϴ� �޼ҵ�
				setArrToString(); //Integer �迭 -> String ��ȯ �޼ҵ�
				setBackColor(); //���ں� ������ ���� �޼ҵ�
				newFrame n = new newFrame(); //game ���� or ���н� ȣ��� class
			}

		});

		rightBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				constructRandNum();
				rightSort();
				setArrToString();
				setBackColor();
				newFrame n = new newFrame();
			}
		});

		upBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				constructRandNum();
				upSort();
				setArrToString();
				setBackColor();
				newFrame n = new newFrame();
			}
		});

		downBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				constructRandNum();
				downSort();
				setArrToString();
				setBackColor();
				newFrame n = new newFrame();
			}
		});

		controlPanel.add(upBtn);
		controlPanel.add(downBtn);
		controlPanel.add(leftBtn);
		controlPanel.add(rightBtn);

		contentPane.add(helpPanel, BorderLayout.SOUTH);
		helpPanel.add(controlPanel);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	//���� ���� ���� �޼ҵ�
	public void constructRandNum() {

		if(isEmpty()) //��� ���� ��, �������ڻ���
			while (true) {
				randNum1 = ranPlace.nextInt(4);
				randNum2 = ranPlace.nextInt(4);
				if (arr[randNum1][randNum2] == 0) {
					arr[randNum1][randNum2] = 2;
					break;
				}
			}
	}

	//�������� ������� ���� ���ڰ� �������� �ǵ��� ������ִ� �޼ҵ�
	boolean isEmpty() {
		for(int i = 0; i < 4; i++)
			for(int j = 0; j < 4; j++)
				if(arr[i][j] == 0) return true;
		return false;
	}

	//�������� �޼ҵ�
	public void leftSort() {
		int i, j, k;
		
		for(i = 0; i < 4; i++){
			for(j = 3; j > 0; j--){ //arr[i][0]���� ���ڸ� �����ؾ� �ϹǷ� �����ʺ��� j = 0�� ���� �� �˻�
				if(arr[i][j] == arr[i][j-1]){ //������ �� �迭�� ������
					arr[i][j-1] = arr[i][j-1]*2; //�������� 2���ؼ� ������
					arr[i][j] = 0; //���� �ִ� �ڸ��� 0���� reset
					j = j - 1; //4444 -> 4480������(�� ���� ����ǵ��� �� �ǳʶ��)
				}
				else if(arr[i][j-1] == 0 ){ //��ĭ�� ��
					arr[i][j-1] = arr[i][j]; // �״�� �ű��
					arr[i][j] = 0; //���� �ִ��ڸ� reset
				}
				for(k = 3; k > 0; k--){ // 4480 -> 8080 -> 8800(�տ� ���� ó�� �� ����ִ� ����(0)�� �����ֱ� ���� ��ġ)
					if(arr[i][k-1] == 0){ //arr[i][j] �������� ���� ĭ�� 0�϶�
						arr[i][k-1] = arr[i][k]; //0�� ������ �����ش�
						arr[i][k] = 0;//���� �ִ� �ڸ� reset
					}
				}
			}
		}
	}

	//������ ���� �޼ҵ�
	public void rightSort() {
		int i, j, k;

		for(i = 0; i < 4; i++){
			for(j = 0; j < 3; j++){ //arr[i][3]���� ���ڸ� �����ؾ� �ϹǷ� ���ʺ��� j = 3�� ���� �� �˻�
				if(arr[i][j] == arr[i][j+1]){ //������ �� �迭 ������
					arr[i][j+1] = arr[i][j+1]*2; //2���ؼ� ����������
					arr[i][j] = 0;
					j = j + 1; //���� ������� �� ���� ������ �� �ֵ��� �� �ǳʶ�(4444 -> 0844)
				}
				else if(arr[i][j+1] == 0 ){ //��ĭ�� ��
					arr[i][j+1] = arr[i][j];
					arr[i][j] = 0;
				}
				for(k = 0; k < 3; k++){ //0844 -> 0808 -> 0088 ����ִ� ���� �����ֱ� ���� ��ġ
					if(arr[i][k+1] == 0){ //arr[i][j] �������� ������ ĭ�� 0�϶�
						arr[i][k+1] = arr[i][k];//0�� ������ �����ش�
						arr[i][k] = 0;
					}
				}
			}
		}
	}

	//���� ���� �޼ҵ�
	public void upSort() {
		int i, j, k;

		for(j = 0; j < 4; j++){ //��� �� �ٲ��ֱ� -> ���ʰ� ��������
			for(i = 3; i > 0; i--){ //arr[0][j]���� ���ڸ� �����ؾ� �ϹǷ� �Ʒ��ʺ��� i = 0�� ���� �� �˻�
				if(arr[i][j] == arr[i-1][j]){ //���� �Ʒ� �� �迭 ��
					arr[i][j] = 0;
					arr[i-1][j] = arr[i-1][j]*2;
					i = i - 1; //���� ����
				}
				else if(arr[i-1][j] == 0 ){
					arr[i-1][j] = arr[i][j];
					arr[i][j] = 0;
				}
				for(k = 3; k > 0; k--){ //4480 -> 8080 -> 8800(���ʰ� ���� ����)
					if(arr[k-1][j] == 0){
						arr[k-1][j] = arr[k][j];
						arr[k][j] = 0;
					}
				}
			}
		} 
	}


	//�Ʒ��� ���� �޼ҵ�
	public void downSort() {
		int i, j, k;

		for(j = 0; j < 4; j++){ //��� �� �ٲ��ֱ� -> �����ʰ� ��������
			for(i = 0; i < 3; i++){ //arr[3][j]���� ���ڸ� �����ؾ� �ϹǷ� ���ʺ��� i = 3�� ���� �� �˻�
				if(arr[i][j] == arr[i+1][j]){
					arr[i][j] = 0;
					arr[i+1][j] = arr[i+1][j]*2;
					i = i + 1;
				}
				else if(arr[i+1][j] == 0 ){
					arr[i+1][j] = arr[i][j];
					arr[i][j] = 0;
				}

				for(k = 0; k < 3; k++){ //0844 -> 0808 -> 0088 (�����ʰ� ���� ����)
					if(arr[k+1][j] == 0){
						arr[k+1][j] = arr[k][j];
						arr[k][j] = 0;
					}
				}
			}
		}

	}

	//Integer �迭 -> String ��ȯ �޼ҵ�
	public void setArrToString() {
		for(int i = 0; i < 4; i++)
			for(int j = 0; j < 4; j++)
				if(arr[i][j] != 0){ //Integer �迭 arr[][]�� �ִ� ���ڰ� 0�� �ƴҶ�, ��Ʈ������ ��ȯ
					numBtn[i][j].setText(String.valueOf(arr[i][j]));
				}else if(arr[i][j] == 0) //0�̸� -> 0���� ��ȯ �ϴ� ���� �ƴ϶� " "�� ��ȯ�ϱ�
					numBtn[i][j].setText(String.valueOf(""));
	}


	//�� ���ں� ��漳�� �޼ҵ�
	public void setBackColor() {
		for(int i = 0; i < 4; i++)
			for(int j = 0; j < 4; j++) 
				if (arr[i][j] == 4)
					numBtn[i][j].setBackground(Color.yellow);
				else if (arr[i][j] == 8)
					numBtn[i][j].setBackground(Color.orange);
				else if (arr[i][j] == 16)
					numBtn[i][j].setBackground(Color.green);
				else if (arr[i][j] == 32)
					numBtn[i][j].setBackground(Color.CYAN);
				else if (arr[i][j] == 64)
					numBtn[i][j].setBackground(Color.blue);
				else if (arr[i][j] == 128)
					numBtn[i][j].setBackground(Color.white);
				else if (arr[i][j] == 256)
					numBtn[i][j].setBackground(Color.pink);
				else if (arr[i][j] == 512)
					numBtn[i][j].setBackground(Color.red);
				else if (arr[i][j] == 1024)
					numBtn[i][j].setBackground(Color.gray);
				else numBtn[i][j].setBackground(null);
	}

	//���� ���� or ���н� ��� �� â class 
	public class newFrame extends JFrame {

		public newFrame(){
			SuccessOrFail();
		}

		public void successFrame() {
			setResizable(false);

			JFrame frame = new JFrame();
			frame.setPreferredSize(new Dimension(300,200));
			frame.setLocation(150,300);
			Container contentPane = frame.getContentPane();

			JPanel panel = new JPanel();
			JLabel label = new JLabel("You Win!");
			font = new Font("SanSerif", Font.BOLD, 20);
			label.setFont(font);
			label.setForeground(Color.BLUE);
			panel.add(label,BorderLayout.CENTER);


			JPanel panel2 = new JPanel();

			//restart ��ư
			JButton restartBtn = new JButton("Restart");
			restartBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (int i = 0; i < 4; i++) {
						for (int j = 0; j < 4; j++) {
							arr[i][j] = 0;  //�迭 ����
							numBtn[i][j].setText(""); //������ ����
							numBtn[i][j].setBackground(null); //��� ����
							frame.setVisible(false);
							frame.dispose(); //�ش� â�� ��������
						}
					}
				}
			});

			//exit ��ư
			JButton exitBtn = new JButton("Exit");
			exitBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0); //���α׷� ������ ����
				}
			});

			panel2.add(restartBtn);
			panel2.add(exitBtn);

			contentPane.add(panel);
			contentPane.add(panel2, BorderLayout.SOUTH);


			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.pack();
			frame.setVisible(true);
		}

		public void failFrame() {
			setResizable(false);

			JFrame frame = new JFrame();
			frame.setPreferredSize(new Dimension(300,200));
			frame.setLocation(150,300);
			Container contentPane = frame.getContentPane();

			JPanel panel = new JPanel();
			JLabel label = new JLabel("Game Over!");
			font = new Font("SanSerif", Font.BOLD, 20);
			label.setFont(font);
			label.setForeground(Color.BLUE);
			panel.add(label);

			JPanel panel2 = new JPanel();

			JButton restartBtn = new JButton("Restart");
			restartBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (int i = 0; i < 4; i++) {
						for (int j = 0; j < 4; j++) {
							arr[i][j] = 0;
							numBtn[i][j].setText("");
							numBtn[i][j].setBackground(null);
							frame.setVisible(false);
							frame.dispose();
						}
					}
				}
			});

			JButton exitBtn = new JButton("Exit");
			exitBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});

			panel2.add(restartBtn);
			panel2.add(exitBtn);

			contentPane.add(panel);
			contentPane.add(panel2, BorderLayout.SOUTH);

			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.pack();
			frame.setVisible(true);

		}

		//�������п��� �Ǻ� �޼ҵ�
		public void SuccessOrFail(){
			int count = 0; //����ִ� ���� ���� count ����

			for(int i = 0; i < 4; i++){
				for(int j = 0; j < 4; j++){
					if(arr[i][j] == 0) count++;
					else if(arr[i][j] == 1024) successFrame(); //1024�� �ִ� �迭 �߽߰� successFrame ����
				}
			}
			if(count == 0) failFrame(); //����ִ� ������ ���� ��� failFrame ����
		}
	}
}


