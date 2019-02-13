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
	JButton[][] numBtn; //숫자판에 생성될 JButton 배열

	JButton upBtn = new JButton("UP");
	JButton downBtn = new JButton("DOWN");
	JButton leftBtn = new JButton("LEFT");
	JButton rightBtn = new JButton("RIGHT");

	Random ranPlace = new Random();

	int randNum1;
	int randNum2;
	int randNum3;
	int randNum4;

	int arr[][] = new int [4][4]; //btnNum[i][j]배열과 호환할 integer 배열 생성


	public GameFrame() {
		JFrame frame = new JFrame("1024 GAME");
		frame.setPreferredSize(new Dimension(600, 800));
		Container contentPane = frame.getContentPane();

		//게임타이틀 GUI
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
						arr[i][j] = 0;  //배열 리셋
						numBtn[i][j].setText(""); //숫자판 리셋
						numBtn[i][j].setBackground(null); //배경 리셋
					}
				}
			}
		});
		panel2.add(restartBtn);

		//초기 랜덤 숫자 설정 버튼
		JButton initializeBtn = new JButton("Initialize");
		initializeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == initializeBtn){

					//버튼 누를 때마다 초기버튼 다시 설정할 수 있도록 처음에 0으로 설정
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

					//숫자 2 랜덤위치 설정
					arr[randNum1][randNum2] = 2;
					numBtn[randNum1][randNum2].setText("2");

					//숫자 4 랜덤위치 설정
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


		numBtn = new JButton[4][4]; //4 X 4 배열의 JButton배열생성

		//gamePanel에 JButton 배열 붙이기
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				numBtn[i][j] = new JButton("");
				numBtn[i][j].setFont(new Font("맑은 고딕", Font.BOLD, 50)); 
				gamePanel.add(numBtn[i][j]);
			}
		}

		contentPane.add(gamePanel, BorderLayout.CENTER);

		//게임설명문구 라벨생성
		JPanel helpPanel = new JPanel(new GridLayout(3, 1));
		JLabel helpLabel = new JLabel("       Click to move. (ex) 2 + 2 = 4. Reach 1024. Good Luck!");
		font = new Font("SanSerif", Font.BOLD, 20);
		helpLabel.setFont(font);
		helpLabel.setForeground(Color.RED);
		helpPanel.add(helpLabel);

		JPanel controlPanel = new JPanel();

		//미리 두 개의 랜덤 숫자 2와 4 출력
		randNum1 = ranPlace.nextInt(4);
		randNum2 = ranPlace.nextInt(4);
		randNum3 = ranPlace.nextInt(4);
		randNum4 = ranPlace.nextInt(4);

		numBtn[randNum1][randNum2].setText("2");
		numBtn[randNum3][randNum4].setText("4");
		numBtn[randNum3][randNum4].setBackground(Color.yellow);

		//4개 방향 각각의 ActionListener
		leftBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {                                                             
				constructRandNum(); //랜덤생성메소드
				leftSort(); //왼쪽으로 숫자를 정렬하는 메소드
				setArrToString(); //Integer 배열 -> String 변환 메소드
				setBackColor(); //숫자별 배경색깔 설정 메소드
				newFrame n = new newFrame(); //game 성공 or 실패시 호출될 class
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

	//랜덤 숫자 생성 메소드
	public void constructRandNum() {

		if(isEmpty()) //비어 있을 때, 랜덤숫자생성
			while (true) {
				randNum1 = ranPlace.nextInt(4);
				randNum2 = ranPlace.nextInt(4);
				if (arr[randNum1][randNum2] == 0) {
					arr[randNum1][randNum2] = 2;
					break;
				}
			}
	}

	//숫자판이 비어있을 때만 숫자가 랜덤생성 되도록 만들어주는 메소드
	boolean isEmpty() {
		for(int i = 0; i < 4; i++)
			for(int j = 0; j < 4; j++)
				if(arr[i][j] == 0) return true;
		return false;
	}

	//왼쪽정렬 메소드
	public void leftSort() {
		int i, j, k;
		
		for(i = 0; i < 4; i++){
			for(j = 3; j > 0; j--){ //arr[i][0]으로 숫자를 정렬해야 하므로 오른쪽부터 j = 0열 빼고 다 검사
				if(arr[i][j] == arr[i][j-1]){ //인접한 두 배열이 같으면
					arr[i][j-1] = arr[i][j-1]*2; //왼쪽으로 2배해서 보내줌
					arr[i][j] = 0; //원래 있던 자리는 0으로 reset
					j = j - 1; //4444 -> 4480까지만(한 번만 수행되도록 열 건너띄기)
				}
				else if(arr[i][j-1] == 0 ){ //빈칸일 때
					arr[i][j-1] = arr[i][j]; // 그대로 옮기기
					arr[i][j] = 0; //원래 있던자리 reset
				}
				for(k = 3; k > 0; k--){ // 4480 -> 8080 -> 8800(앞에 포문 처리 후 비어있는 공간(0)은 땡겨주기 위한 장치)
					if(arr[i][k-1] == 0){ //arr[i][j] 기준으로 왼쪽 칸이 0일때
						arr[i][k-1] = arr[i][k]; //0인 곳으로 땡겨준다
						arr[i][k] = 0;//원래 있던 자리 reset
					}
				}
			}
		}
	}

	//오른쪽 정렬 메소드
	public void rightSort() {
		int i, j, k;

		for(i = 0; i < 4; i++){
			for(j = 0; j < 3; j++){ //arr[i][3]으로 숫자를 정렬해야 하므로 왼쪽부터 j = 3열 빼고 다 검사
				if(arr[i][j] == arr[i][j+1]){ //인접한 두 배열 같으면
					arr[i][j+1] = arr[i][j+1]*2; //2배해서 오른쪽으로
					arr[i][j] = 0;
					j = j + 1; //같은 방식으로 한 번만 합쳐질 수 있도록 열 건너띔(4444 -> 0844)
				}
				else if(arr[i][j+1] == 0 ){ //빈칸일 때
					arr[i][j+1] = arr[i][j];
					arr[i][j] = 0;
				}
				for(k = 0; k < 3; k++){ //0844 -> 0808 -> 0088 비어있는 공간 땡겨주기 위한 장치
					if(arr[i][k+1] == 0){ //arr[i][j] 기준으로 오른쪽 칸이 0일때
						arr[i][k+1] = arr[i][k];//0인 곳으로 땡겨준다
						arr[i][k] = 0;
					}
				}
			}
		}
	}

	//위쪽 정렬 메소드
	public void upSort() {
		int i, j, k;

		for(j = 0; j < 4; j++){ //행과 열 바꿔주기 -> 왼쪽과 같은원리
			for(i = 3; i > 0; i--){ //arr[0][j]으로 숫자를 정렬해야 하므로 아래쪽부터 i = 0행 빼고 다 검사
				if(arr[i][j] == arr[i-1][j]){ //위와 아래 두 배열 비교
					arr[i][j] = 0;
					arr[i-1][j] = arr[i-1][j]*2;
					i = i - 1; //위와 동일
				}
				else if(arr[i-1][j] == 0 ){
					arr[i-1][j] = arr[i][j];
					arr[i][j] = 0;
				}
				for(k = 3; k > 0; k--){ //4480 -> 8080 -> 8800(왼쪽과 같은 원리)
					if(arr[k-1][j] == 0){
						arr[k-1][j] = arr[k][j];
						arr[k][j] = 0;
					}
				}
			}
		} 
	}


	//아래쪽 정렬 메소드
	public void downSort() {
		int i, j, k;

		for(j = 0; j < 4; j++){ //행과 열 바꿔주기 -> 오른쪽과 같은원리
			for(i = 0; i < 3; i++){ //arr[3][j]으로 숫자를 정렬해야 하므로 위쪽부터 i = 3행 빼고 다 검사
				if(arr[i][j] == arr[i+1][j]){
					arr[i][j] = 0;
					arr[i+1][j] = arr[i+1][j]*2;
					i = i + 1;
				}
				else if(arr[i+1][j] == 0 ){
					arr[i+1][j] = arr[i][j];
					arr[i][j] = 0;
				}

				for(k = 0; k < 3; k++){ //0844 -> 0808 -> 0088 (오른쪽과 같은 원리)
					if(arr[k+1][j] == 0){
						arr[k+1][j] = arr[k][j];
						arr[k][j] = 0;
					}
				}
			}
		}

	}

	//Integer 배열 -> String 변환 메소드
	public void setArrToString() {
		for(int i = 0; i < 4; i++)
			for(int j = 0; j < 4; j++)
				if(arr[i][j] != 0){ //Integer 배열 arr[][]에 있는 숫자가 0이 아닐때, 스트링으로 변환
					numBtn[i][j].setText(String.valueOf(arr[i][j]));
				}else if(arr[i][j] == 0) //0이면 -> 0으로 변환 하는 것이 아니라 " "로 변환하기
					numBtn[i][j].setText(String.valueOf(""));
	}


	//각 숫자별 배경설정 메소드
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

	//게임 성공 or 실패시 띄울 새 창 class 
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

			//restart 버튼
			JButton restartBtn = new JButton("Restart");
			restartBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (int i = 0; i < 4; i++) {
						for (int j = 0; j < 4; j++) {
							arr[i][j] = 0;  //배열 리셋
							numBtn[i][j].setText(""); //숫자판 리셋
							numBtn[i][j].setBackground(null); //배경 리셋
							frame.setVisible(false);
							frame.dispose(); //해당 창만 닫히도록
						}
					}
				}
			});

			//exit 버튼
			JButton exitBtn = new JButton("Exit");
			exitBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0); //프로그램 완전히 종료
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

		//성공실패여부 판별 메소드
		public void SuccessOrFail(){
			int count = 0; //비어있는 공간 세는 count 변수

			for(int i = 0; i < 4; i++){
				for(int j = 0; j < 4; j++){
					if(arr[i][j] == 0) count++;
					else if(arr[i][j] == 1024) successFrame(); //1024가 있는 배열 발견시 successFrame 띄우기
				}
			}
			if(count == 0) failFrame(); //비어있는 공간이 없을 경우 failFrame 띄우기
		}
	}
}


