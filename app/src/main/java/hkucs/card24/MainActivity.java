package hkucs.card24;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.singularsys.jep.EvaluationException;
import com.singularsys.jep.Jep;
import com.singularsys.jep.ParseException;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    ImageButton[] cards;
    Button rePick;
    Button checkInput;
    Button clear;
    Button left;
    Button right;
    Button plus;
    Button minus;
    Button multiply;
    Button divide;
    int count;
    TextView expression;
    int[] data;
    int[] card;
    int[] imageCount;
    int goal;
    String lastPressed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rePick = (Button)findViewById(R.id.repick);
        checkInput = (Button)findViewById(R.id.checkinput);
        left = (Button)findViewById(R.id.left);
        right = (Button)findViewById(R.id.right);
        plus = (Button)findViewById(R.id.plus);
        minus = (Button)findViewById(R.id.minus);
        multiply = (Button)findViewById(R.id.multiply);
        divide = (Button)findViewById(R.id.divide);
        clear = (Button)findViewById(R.id.clear);
        expression = (TextView)findViewById(R.id.input);
        cards = new ImageButton[4];
        cards[0] = (ImageButton) findViewById(R.id.card1);
        cards[1] = (ImageButton) findViewById(R.id.card2);
        cards[2] = (ImageButton) findViewById(R.id.card3);
        cards[3] = (ImageButton) findViewById(R.id.card4);
        Bundle bundle = getIntent().getExtras();
        String num = bundle.getString("input");
        //System.out.println("The int is " + num);
        goal = Integer.parseInt(num);
        expression.setHint("Please form an expression s.t the result is " + goal);
        initCardImage();
        pickCard();
        setListeners();
    }
    private void setListeners(){
        cards[0].setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View view) {
                if(lastPressed == "" || lastPressed=="airth" || lastPressed=="("){
                    lastPressed = "card";
                    clickCard(0);
                }
                else {
                    Toast.makeText(MainActivity.this, "Invalid",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        cards[1].setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View view) {
                if(lastPressed == "" || lastPressed=="airth" || lastPressed=="("){
                    lastPressed = "card";
                    clickCard(1);
                }
                else {
                    Toast.makeText(MainActivity.this, "Invalid",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        cards[2].setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View view) {
                if(lastPressed == "" || lastPressed=="airth" || lastPressed=="("){
                    lastPressed = "card";
                    clickCard(2);
                }
                else {
                    Toast.makeText(MainActivity.this, "Invalid",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        cards[3].setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View view) {
                if(lastPressed == "" || lastPressed=="airth" || lastPressed=="("){
                    lastPressed = "card";
                    clickCard(3);
                }
                else {
                    Toast.makeText(MainActivity.this, "Invalid",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        left.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view) {
                if(lastPressed == "" || lastPressed=="airth" || lastPressed=="("){
                    lastPressed = "(";
                    expression.append("(");
                }
                else {
                    Toast.makeText(MainActivity.this, "Invalid",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        right.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View view) {
                if(lastPressed == ")" || lastPressed =="card"){
                    lastPressed = ")";
                    expression.append(")");
                }
                else {
                    Toast.makeText(MainActivity.this, "Invalid",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        plus.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view) {
                if(lastPressed == ")" || lastPressed =="card"){
                    lastPressed = "airth";
                    expression.append("+");
                }
                else {
                    Toast.makeText(MainActivity.this, "Invalid",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        minus.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view) {
                if(lastPressed == ")" || lastPressed =="card"){
                    lastPressed = "airth";
                    expression.append("-");
                }
                else {
                    Toast.makeText(MainActivity.this, "Invalid",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        multiply.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view) {
                if(lastPressed == ")" || lastPressed =="card"){
                    lastPressed = "airth";
                    expression.append("*");
                }
                else {
                    Toast.makeText(MainActivity.this, "Invalid",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        divide.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view) {
                if(lastPressed == ")" || lastPressed =="card"){
                    lastPressed = "airth";
                    expression.append("/");
                }
                else {
                    Toast.makeText(MainActivity.this, "Invalid",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        clear.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){

                setClear();
            }
        });
        checkInput.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view) {
                String inputStr = expression.getText().toString();
                if (checkInput(inputStr)==2) {
                    Toast.makeText(MainActivity.this, "Correct Answer",
                            Toast.LENGTH_SHORT).show();
                    pickCard();
                } else if(checkInput(inputStr)==0){
                    Toast.makeText(MainActivity.this, "Wrong Answer",
                            Toast.LENGTH_SHORT).show();
                    setClear();
                }
                else {
                    Toast.makeText(MainActivity.this, "Please use all 4 cards!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void initCardImage(){
        for (int i = 0; i < 4; i++) {
            int resID = getResources().getIdentifier("back_0","drawable", MainActivity.this.getPackageName());
            cards[i].setImageResource(resID);
        }
    }
    private void pickCard(){
        data = new int[4];
        card = new int[4];
        int number;
        Stack<Integer> numbers = randomGenerator();
        for (int i=0;i<4;i++){
            number = numbers.pop();
            card[i] = number;
            if(number>13 && number<27){
                data[i] = number-13;
            }
            else if(number>26 && number<40){
                data[i] = number-26;
            }
            else if(number>39 && number<53){
                data[i] = number-39;
            }
            else{
                data[i] = number;
            }
        }
        setClear();
    }
    private Stack<Integer> randomGenerator(){
        Random r = new Random();
        Stack<Integer> uniqueNumbers = new Stack<>();
        while (uniqueNumbers.size()<4){
            uniqueNumbers.add(r.nextInt(52)+1);
        }
        return uniqueNumbers;
    }
    private void setClear(){
        int resID;
        imageCount = new int[4];
        expression.setText("");
        count = 0;
        lastPressed = "";
        for (int i = 0; i < 4; i++) {
            imageCount[i] = 0;
            resID = getResources().getIdentifier("card"+card[i],"drawable", MainActivity.this.getPackageName());
            cards[i].setImageResource(resID);
            cards[i].setClickable(true);
        }
    }
    private void clickCard(int i) {
        int resId;
        String num;
        Integer value;
        if (imageCount[i] == 0) {
            resId = getResources().getIdentifier("back_0","drawable", MainActivity.this.getPackageName());
            cards[i].setImageResource(resId);
            cards[i].setClickable(false);
            count++;
            value = data[i];
            num = value.toString();
            expression.append(num);
            imageCount[i] ++;
        }
    }
    private int checkInput(String input) {
        if(count==4){

            Jep jep = new Jep();
            Object res;
            try {
                jep.parse(input);
                res = jep.evaluate();
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this,
                        "Wrong Expression", Toast.LENGTH_SHORT).show();
                return 0;
            } catch (EvaluationException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this,
                        "Wrong Expression", Toast.LENGTH_SHORT).show();
                return 0;
            }
            Double ca = (Double)res;
            if (Math.abs(ca - goal) < 1e-6)
                return 2;
            return 0;
        }
        else {
            return 1;
        }


    }

}
