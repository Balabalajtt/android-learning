package com.example.calculator;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity{


    private String expresion = "";
    private String answer = "";
    private String num1;
    private String num2;

    private String expresion1;
    private String expresion2;
    private String expresionAns;

    private TextView textView;

    private Button equalButton;
    private Button deleteButton;
    private Button addButton;
    private Button subButton;
    private Button mulButton;
    private Button divButton;
    private Button pointButton;
    private String mString = "对不起 算不来";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.up_text_view);

       /*数字*/
        Resources res = getResources();
        for(int i = 0; i < 10; i++)
        {

            int txv_id = res.getIdentifier("num"+i+"_button", "id", getPackageName());

            final Button btn = (Button) findViewById(txv_id);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    zanshi();
                    expresion = expresion + btn.getText().toString();
                    textView.setText(expresion);
                }
            });
        }

/*符号*/
        pointButton = (Button) findViewById(R.id.point_button);
        pointButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zanshi();
                int flag=0;
                for(int i = expresion.length()-1; i>=0; i--)
                {
                    char l = expresion.charAt(i);
                    if(l == '.')
                    {
                        flag = 1;
                        break;
                    }
                    if (l == '+' || l == '-' ||l == '*' ||l == '/')
                    {
                        if(flag == 0)
                        {
                            break;
                        }
                    }
                }

                if(expresion.equals("") || (expresion.charAt(expresion.length()-1) != '.' && flag == 0))
                {
                    expresion = expresion + ".";
                    textView.setText(expresion);
                }
            }
        });

        addButton = (Button) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zanshi();
                if(expresion.equals(""))
                {
                    return;
                }
                char l = expresion.charAt(expresion.length()-1);
                if( l != '+' && l != '-' && l != '*' && l != '/' )
                {
                    expresion = expresion + "+";
                    textView.setText(expresion);
                }
                else if( l == '-' &&expresion.length()>=2)
                {
                    char a = expresion.charAt(expresion.length()-2);
                    if(a == '+' || a == '-' ||a == '*' ||a == '/' )
                    {
                        expresion = expresion.substring(0,expresion.length()-2);
                        expresion = expresion + "+";
                        textView.setText(expresion);
                    }

                }
                else
                {
                    expresion = expresion.substring(0,expresion.length()-1);
                    expresion = expresion + "+";
                    textView.setText(expresion);
                }
            }
        });

        subButton = (Button) findViewById(R.id.sub_button);
        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zanshi();
                if(expresion.equals(""))
                {
                    expresion = expresion + "-";
                    textView.setText(expresion);
                    return;
                }
                char l = expresion.charAt(expresion.length()-1);
                if( l != '+' && l != '-' )
                {
                    expresion = expresion + "-";
                    textView.setText(expresion);
                }
                else
                {
                    expresion = expresion.substring(0,expresion.length()-1);
                    expresion = expresion + "-";
                    textView.setText(expresion);
                }
            }
        });

        mulButton = (Button) findViewById(R.id.mul_button);
        mulButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zanshi();
                if(expresion.equals(""))
                {
                    return;
                }
                char l = expresion.charAt(expresion.length()-1);
                if( l != '+' && l != '-' && l != '*' && l != '/' )
                {
                    expresion = expresion + "*";
                    textView.setText(expresion);
                }
                else if(l == '-' && expresion.length()>=2)
                {
                    char a = expresion.charAt(expresion.length()-2);
                    if(a == '+' || a == '-' ||a == '*' ||a == '/' )
                    {
                        expresion = expresion.substring(0,expresion.length()-2);
                        expresion = expresion + "*";
                        textView.setText(expresion);
                    }

                }
                else
                {
                    expresion = expresion.substring(0,expresion.length()-1);
                    expresion = expresion + "*";
                    textView.setText(expresion);
                }
            }
        });

        divButton = (Button) findViewById(R.id.div_button);
        divButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zanshi();
                if(expresion.equals(""))
                {
                    return;
                }
                char l = expresion.charAt(expresion.length()-1);
                if( l != '+' && l != '-' && l != '*' && l != '/' )
                {
                    expresion = expresion + "/";
                    textView.setText(expresion);
                }
                else if( l == '-' &&expresion.length()>=2)
                {
                    char a = expresion.charAt(expresion.length()-2);
                    if(a == '+' || a == '-' ||a == '*' ||a == '/' )
                    {
                        expresion = expresion.substring(0,expresion.length()-2);
                        expresion = expresion + "/";
                        textView.setText(expresion);
                    }

                }
                else
                {
                    expresion = expresion.substring(0,expresion.length()-1);
                    expresion = expresion + "/";
                    textView.setText(expresion);
                }
            }
        });




        deleteButton = (Button) findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zanshi();
                if(!expresion.equals(""))
                expresion = expresion.substring(0,expresion.length()-1);
                textView.setText(expresion);
            }
        });
        deleteButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                expresion = "";
                textView.setText(expresion);
                return true;
            }
        });

        equalButton = (Button) findViewById(R.id.equal_button);
        equalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"计算功能暂未实现",Toast.LENGTH_SHORT).show();
                //expresion = mString;
                Count1 ();
                //textView.setText(expresion);
            }
        });

    }

    public void zanshi()
    {
        if(expresion.equals(mString))
        {
            expresion = "";
            textView.setText(expresion);
        }
    }


    public boolean Count1 ()
    {
        num1 = num2 = "";
        for(int i = 0, index1 = 0,index2; i<expresion.length(); i++)
        {
            char l = expresion.charAt(i);
            if(l == '+'|| l == '-' || l == '*' || l == '/')
            {
                if(l=='*'||l=='/')
                {
                    num1 = num2;
                    num2 = "";
                    for(i = i+1;i<expresion.length();i++)
                    {
                        char a = expresion.charAt(i);
                        if(a == '+'|| a == '-' || a == '*' || a == '/')
                        {
                            index2 = i-1;
                            if (l == '*')
                            {
                                answer = String.valueOf(Float.parseFloat(num1)*Float.parseFloat(num2));
                                expresion1 = expresion.substring(0,index1+1);
                                expresion2 = expresion.substring(index2+1,expresion.length());
                                expresionAns =  expresion1 + answer + expresion2;
                                Toast.makeText(this,expresionAns,Toast.LENGTH_LONG).show();
                                num1 = num2 = "";
                            }
                            else if (l == '/')
                            {
                                if(!num2.equals("0"))
                                {
                                    answer = String.valueOf(Float.parseFloat(num1) / Float.parseFloat(num2));
                                    expresion = expresion.substring(0, index1) + answer + expresion.substring(index2, expresion.length() - 1);
                                    num1 = num2 = "";
                                }
                                else
                                {
                                    return false;
                                }
                            }

                        }
                        else
                        {
                            num2 = num2 + String.valueOf(a);
                        }
                    }
                }
                else
                {
                    index1 = i;
                    num2 = "";
                }
            }
            else
            {
                num2 = num2 + String.valueOf(l);
            }

        }
        return true;
    }


}
