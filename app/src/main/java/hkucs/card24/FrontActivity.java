package hkucs.card24;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ContentFrameLayout;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class FrontActivity extends AppCompatActivity {
    int number;
    Button startButton;
    Context context;
    EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);
        context = this;
        startButton = (Button)findViewById(R.id.start_button);
        setListeners();
    }
    private void setListeners(){
        startButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                et= (EditText) findViewById(R.id.inputNumber);
                String num = et.getText().toString();
                Intent i=  new Intent(context, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("input", num );
                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }
}
