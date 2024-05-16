package com.sa.caloriecalc;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    String [] items ={"Basal Metabolic Rate (BMR)","Sedentary: little or no exercise","Light: exercise 1-3 times/week","Moderate: exercise 4-5 times/week","Active: daily exercise or intense exercise 3-4 times/week"};

    AutoCompleteTextView autoCompleteTextView;

    ArrayAdapter<String> adapteritems;

    TextView ageid,heightid,weightid,caloriesanswertext;
    RadioButton male,female;

    Button calculateBtn,clearBtn;

    String age,height,weight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ageid=findViewById(R.id.agetext);
        heightid=findViewById(R.id.heighttext);
        weightid=findViewById(R.id.weighttext);
        caloriesanswertext=findViewById(R.id.caloriesdayanswer);
        male=findViewById(R.id.selectedmale);
        female=findViewById(R.id.selectedfemale);
        calculateBtn=findViewById(R.id.calculatebutton);
        clearBtn=findViewById(R.id.clearbutton);

        autoCompleteTextView=findViewById(R.id.auto_complate_txt);
        adapteritems = new ArrayAdapter<String>(this,R.layout.list_item,items);

        autoCompleteTextView.setAdapter(adapteritems);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String item = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(MainActivity.this, "Item: "+item, Toast.LENGTH_SHORT).show();

            }
        });

        calculateBtn.setOnClickListener(v -> {
            age = ageid.getText().toString();
            height = heightid.getText().toString();
            weight=weightid.getText().toString();

            if(TextUtils.isEmpty(age))
                ageid.setError("Age dont exist!");

            if(TextUtils.isEmpty(height))
                heightid.setError("Height dont exist!");

            if(TextUtils.isEmpty(weight))
                weightid.setError("Weight dont exist!");


            String selectedOption = autoCompleteTextView.getText().toString();
            int position = adapteritems.getPosition(selectedOption);
            calculate(bazalhesapla(),position);

        });


        clearBtn.setOnClickListener(v -> {

            ageid.setText("");
            heightid.setText("");
            weightid.setText("");
            caloriesanswertext.setText("");
            if(male.isChecked())
                male.setChecked(false);
            else
                female.setChecked(false);
        });


    }

    private float bazalhesapla(){

        float sonuc = 0;

        if(male.isChecked()){
            sonuc=66.5f+(13.75f*Integer.valueOf(weight))+(5*Integer.valueOf(height))-(6.77f*Integer.valueOf(age));
        }
        else{
            sonuc=665.1f+(9.56f*Integer.valueOf(weight))+(1.85f*Integer.valueOf(height))-(4.67f*Integer.valueOf(age));
        }

        return sonuc;
    }

    private void calculate(float bazalsonuc,int position){

        float sonuc =0;

        switch (position){
            case 0:
                sonuc=bazalsonuc;
                break;

            case 1:
                sonuc=bazalsonuc*1.2f;
                break;

            case 2:
                sonuc=bazalsonuc*1.3f;
                break;

            case 3:
                sonuc=bazalsonuc*1.4f;
                break;

            case 4:
                sonuc=bazalsonuc*1.5f;
                break;

            default:
                Toast.makeText(MainActivity.this, "Error!",Toast.LENGTH_SHORT).show();
                break;
        }

        caloriesanswertext.setText(sonuc+" Calories/day");

    }


}