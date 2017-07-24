package com.example.calculator;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.StringTokenizer;

public strictfp class MainActivity extends AppCompatActivity{


    String expresion = "";

    private int flag=0;
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

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.hide();
        }

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
                    if(flag==1)
                    {
                        expresion = "";
                        textView.setText(expresion);
                        flag = 0;
                    }
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
                if(flag==1)
                {
                    expresion = "";
                    textView.setText(expresion);
                    flag = 0;
                }
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
                if(flag==1)
                {
                    textView.setText(expresion);
                    flag = 0;
                }
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
                else if(l == '-' &&expresion.length()==1)
                {
                    return;
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
                if(flag==1)
                {
                    textView.setText(expresion);
                    flag = 0;
                }
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
                if(flag==1)
                {
                    textView.setText(expresion);
                    flag = 0;
                }
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
                else if(l == '-' &&expresion.length()==1)
                {
                    return;
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
                if(flag==1)
                {
                    textView.setText(expresion);
                    flag = 0;
                }
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
                else if(l == '-' &&expresion.length()==1)
                {
                    return;
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
                if(flag==1)
                {
                    expresion = "";
                    textView.setText(expresion);
                    flag = 0;
                }
                zanshi();
                if(!expresion.equals(""))
                expresion = expresion.substring(0,expresion.length()-1);
                textView.setText(expresion);
            }
        });
        deleteButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(flag==1)
                {
                    expresion = "";
                    textView.setText(expresion);
                    flag = 0;
                }
                expresion = "";
                textView.setText(expresion);
                return true;
            }
        });

        equalButton = (Button) findViewById(R.id.equal_button);
        equalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == 0) {
                    int nei=0;
                    for(int i = 0; i<expresion.length(); i++)
                    {
                        char a = expresion.charAt(i);
                        if(a=='+'||a=='-'||a=='*'||a=='/')
                        {
                            nei=1;break;
                        }
                    }

                    if(nei==0)return;
                    if(expresion.equals("-"))return;

                    quwei();
                    if(chushi()==false)
                    {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                        dialog.setTitle("真会玩");
                        dialog.setMessage("怎么能这么欺负小数点！");
                        dialog.setCancelable(false);
                        dialog.setPositiveButton("知道了，对不起我傻", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        dialog.show();
                        flag = 1;
                        textView.setText("错误");
                        expresion="";
                        return;
                    }



                    while (chengchu()) {
                        if (Count1() == false)
                        {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                            dialog.setTitle("运算错误");
                            dialog.setMessage("是不是傻，0不能作为除数！");
                            dialog.setCancelable(false);
                            dialog.setPositiveButton("知道了，对不起我傻", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            dialog.show();
                            flag = 1;
                            textView.setText("错误");
                            expresion="";
                            return;
                        }
                        if (!judge()) {
                            break;
                        }
                    }

                    fuhao();
                    while (!zuihou()) {
                        Count2();
                        fuhao();
                    }
                    //Toast.makeText(MainActivity.this, "cheng" + expresion, Toast.LENGTH_SHORT).show();

                    flag = 1;
                    textView.setText(expresion);
                    Log.i("33","55");
                    //expresion = mString;
//                Count1 ();
                    //textView.setText(expresion);
                }
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

    public void quwei()
    {
        char a;
        while((a = expresion.charAt(expresion.length()-1))=='+'||a=='-'||a=='*'||a=='/')
        {
            expresion = expresion.substring(0,expresion.length()-1);
        }

    }

    public boolean Count1 ()
    {
        String answer;
        String expresionAns;
        char a='*',b;
        int n,i,j;
        //找到乘除号
        //25*47
        //i n j
        for(n=0; n<expresion.length(); n++)
        {
            a = expresion.charAt(n);
            if(a=='*'||a=='/')
            {
                break;
            }
        }

        for(i=n-1; i>=0; i--)
        {
            b = expresion.charAt(i);
            if(b=='+')
            {
                i++;
                break;
            }
            else if(b=='-')
            {
                if(i==0 || (i!=0 && (expresion.charAt(i-1)=='*'||expresion.charAt(i-1)=='/')))
                {
                    break;
                }
                else
                {
                    i++;
                    break;
                }
            }
            else if(i==0)
            {
                break;
            }
        }

        for(j=n+1;j<expresion.length();j++)
        {
            b = expresion.charAt(j);
            if(j==expresion.length()-1)
            {
                break;
            }
            else if(b=='+'||b=='-'||b=='*'||b=='/')
            {
                if(b=='-' && (expresion.charAt(j-1)=='*'||expresion.charAt(j-1)=='/'))
                {
                    continue;
                }
                j--;
                break;
            }
        }


        //Toast.makeText(this,""+i+n+j,Toast.LENGTH_LONG).show();
        if(a=='*')
        {
            BigDecimal num1 = new BigDecimal(expresion.substring(i, n));
            BigDecimal num2 = new BigDecimal(expresion.substring(n + 1, j + 1));
            answer = "" + num1.multiply(num2);
            //answer = String.valueOf(Float.parseFloat(expresion.substring(i, n)) * Float.parseFloat(expresion.substring(n + 1, j + 1)));

        }
        else
        {
            if(Float.parseFloat(expresion.substring(n + 1, j + 1))==0)
            {
                return false;
            }
            else
            {

                BigDecimal num1 = BigDecimal.valueOf(Double.parseDouble(expresion.substring(i, n)));
                BigDecimal num2 = BigDecimal.valueOf(Double.parseDouble(expresion.substring(n + 1, j + 1)));
                answer = "" + num1.divide(num2,10, BigDecimal.ROUND_HALF_UP).doubleValue();
//                answer = String.valueOf(Float.parseFloat(expresion.substring(i, n)) / Float.parseFloat(expresion.substring(n + 1, j + 1)));
            }
        }


        if(i!=0)
        {
            expresionAns = expresion.substring(0, i) + answer;
        }
        else
        {
            expresionAns = answer;
        }
        if(j<expresion.length()-1)
        {
            expresionAns = expresionAns + expresion.substring(j + 1, expresion.length());
        }

        //Toast.makeText(this,expresionAns,Toast.LENGTH_LONG).show();
        expresion = expresionAns;
        return true;

//        Toast.makeText(this,"此时"+expresion,Toast.LENGTH_LONG).show();
    }

    public boolean judge()
    {
        for(int i=0; i<expresion.length();i++)
        {
            char a = expresion.charAt(i);
            if(a=='*'||a=='/')
            {
                return true;
            }
        }
        return false;
    }


    public void fuhao()
    {
        expresion = expresion.replaceAll("--","+");
        while(expresion.contains("++"))
        {
            expresion = expresion.replaceAll("\\u002B\\u002B", "+");
        }
        expresion = expresion.replaceAll("\\u002B-","-");

    }




    public void Count2()
    {
        int i,j;
        char a = '+';
        for(i=0; i<expresion.length(); i++)
        {
            a = expresion.charAt(i);
            if((a=='+'||a=='-')&&i!=0)
            {
                break;
            }
        }
        for(j = i+1;j<expresion.length();j++)
        {
            char b = expresion.charAt(j);
            if(j==expresion.length()-1||(b=='+'||b=='-'))
            {
                if(j!=expresion.length()-1)
                {
                    j--;
                }
                break;
            }
        }

        String answer;
        if(a=='+')
        {

            BigDecimal num1 = new BigDecimal(expresion.substring(0,i));
            BigDecimal num2 = new BigDecimal(expresion.substring(i+1,j+1));
            answer = "" + num1.add(num2);
            //answer = String.valueOf(Float.parseFloat(expresion.substring(0, i)) + Float.parseFloat(expresion.substring(i+1, j + 1)));
        }
        else
        {

            BigDecimal num1 = new BigDecimal(expresion.substring(0,i));
            BigDecimal num2 = new BigDecimal(expresion.substring(i + 1, j + 1));
            answer = "" + num1.subtract(num2);
            //answer = String.valueOf(Float.parseFloat(expresion.substring(0, i)) - Float.parseFloat(expresion.substring(i+1, j + 1)));
        }

        expresion = answer + expresion.substring(j+1,expresion.length());
        Toast.makeText(this,expresion,Toast.LENGTH_LONG);
    }


    public boolean zuihou()
    {
        for(int i=0;i<expresion.length();i++)
        {
            if(i!=0&&(expresion.charAt(i)=='+'||expresion.charAt(i)=='-'))
            {
                return false;
            }
        }
        return true;
    }

    public boolean chengchu()
    {
        for(int i = 0;i<expresion.length();i++)
        {
            char a = expresion.charAt(i);
            if(a=='*'||a=='/')
            {
                return true;
            }
        }
        return false;
    }

    public boolean chushi()
    {
        for(int i = 0;i<expresion.length(); i++)
        {
            char a=expresion.charAt(i);
            if(a=='.')
            {
                if(expresion.length()==1)
                {
                    return false;
                }
                else if(i==0)
                {
                    char b = expresion.charAt(i+1);
                    if(b=='+'||b=='-'||b=='*'||b=='/')
                    {
                        return false;
                    }
                }
                else if(i==expresion.length()-1)
                {
                    char b = expresion.charAt(i-1);
                    if(b=='+'||b=='-'||b=='*'||b=='/')
                    {
                        return false;
                    }
                }
                else
                {
                    char m = expresion.charAt(i+1);
                    char n = expresion.charAt(i-1);
                    if((m=='+'||m=='-'||m=='*'||m=='/')&&(n=='+'||n=='-'||n=='*'||n=='/'))
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }



}
