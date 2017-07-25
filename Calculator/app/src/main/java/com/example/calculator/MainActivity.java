package com.example.calculator;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity{


    private Toast mToast;

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
    private Button leftButton;
    private Button rightButton;


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

                    expresion = expresion + btn.getText().toString();
                    textView.setText(expresion);

                }
            });
        }

/*符号*/

        leftButton = (Button) findViewById(R.id.left_button);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flag==1)
                {
                    textView.setText(expresion);
                    flag = 0;
                }

                char a,b;
                if(expresion.length()==0)
                {
                    expresion = expresion + "(";
                    textView.setText(expresion);
                    return ;
                }
                else if(expresion.length()==1)
                {
                    a = expresion.charAt(expresion.length()-1);
                    if (a == '.')
                    {
                        showToast("小数点使用错误");
                        return ;
                    }
                }
                else
                {
                    a = expresion.charAt(expresion.length()-1);
                    b = expresion.charAt(expresion.length()-2);
                    if(a=='.'&& !Character.isDigit(b))
                    {
                        showToast("小数点使用错误");
                        return ;
                    }
                    else
                    {
                        expresion = expresion + "(";
                        textView.setText(expresion);
                        return;
                    }
                }

                expresion = expresion + "(";
                textView.setText(expresion);


            }
        });


        rightButton = (Button) findViewById(R.id.right_button);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flag==1)
                {
                    expresion = "";
                    textView.setText(expresion);
                    flag = 0;
                }

                char a,b;
                if(expresion.length()==0)
                {
                    showToast("括号不匹配");
                    return ;
                }
                else if(expresion.length()==1)
                {
                    a = expresion.charAt(expresion.length()-1);
                    if(a=='(')
                    {
                        showToast("括号内容为空");
                        return;
                    }
                    else
                    {
                        showToast("括号不匹配");
                        return;
                    }
                }
                else
                {
                    a = expresion.charAt(expresion.length()-1);
                    b = expresion.charAt(expresion.length()-2);
                    if(a=='(')
                    {
                        showToast("括号内容为空");
                        return;
                    }
                    if(a=='+'||a=='-'||a=='*'||a=='/')
                    {
                        showToast("括号内容不完整");
                        return;
                    }
                    if(a=='.'&& !Character.isDigit(b))
                    {
                        showToast("小数点使用错误");
                        return ;
                    }
                    else
                    {
                        if(count(expresion,'(')<count(expresion,')')+1)
                        {
                            showToast("括号不匹配");
                            return;
                        }
                    }

                }

                expresion = expresion + ")";
                textView.setText(expresion);


            }
        });





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

                if(expresion.length()!=0)
                {
                    if( expresion.charAt(expresion.length()-1) == ')' )
                    {
                        showToast("小数点使用不正确");
                        return;
                    }
                }


                /*判断之前有没有过小数点*/
                int f=0;
                for(int i = expresion.length()-1; i>=0; i--)
                {
                    char l = expresion.charAt(i);
                    if(l == '.')
                    {
                        f = 1;
                        break;
                    }
                    if (l == '+' || l == '-' ||l == '*' ||l == '/' ||l=='('||l==')')
                    {
                        if(f == 0)
                        {
                            break;
                        }
                    }
                }

                if(f == 0)
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

                if(expresion.equals(""))
                {
                    return;
                }
                char l = expresion.charAt(expresion.length()-1);
                if( l != '+' && l != '-' && l != '*' && l != '/' &&l!='('&&l!='.')
                {
                    expresion = expresion + "+";
                    textView.setText(expresion);
                }
                else if( l == '-' &&expresion.length()>=2)
                {
                    char a = expresion.charAt(expresion.length()-2);
                    if(a == '+' || a == '-' ||a == '*' ||a == '/' || a=='(' || a==')')
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
                else if(l=='(')
                {
                    return;
                }
                else if(l=='.')
                {
                    if(expresion.length()==1)
                    {
                        showToast("小数点使用错误");
                    }
                    else
                    {
                        char b = expresion.charAt(expresion.length()-2);
                        if(!Character.isDigit(b))
                        {
                            showToast("小数点使用错误");
                        }
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
                if(flag==1)
                {
                    textView.setText(expresion);
                    flag = 0;
                }

                if(expresion.equals(""))
                {
                    expresion = expresion + "-";
                    textView.setText(expresion);
                    return;
                }
                char l = expresion.charAt(expresion.length()-1);
                if( l != '+' && l != '-' && l!='.')
                {
                    expresion = expresion + "-";
                    textView.setText(expresion);
                }
                else if(l=='.')
                {
                    if(expresion.length()==1)
                    {
                        showToast("小数点使用错误");
                    }
                    else
                    {
                        char b = expresion.charAt(expresion.length()-2);
                        if(!Character.isDigit(b))
                        {
                            showToast("小数点使用错误");
                        }
                    }
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
                if(expresion.equals(""))
                {
                    return;
                }
                char l = expresion.charAt(expresion.length()-1);
                if( l != '+' && l != '-' && l != '*' && l != '/' && l !='.' && l!='(')
                {
                    expresion = expresion + "*";
                    textView.setText(expresion);
                }
                else if(l == '-' && expresion.length()>=2)
                {
                    char a = expresion.charAt(expresion.length()-2);
                    if(a == '+' || a == '-' ||a == '*' ||a == '/'||a=='(' ||a==')')
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
                else if(l=='(')
                {
                    showToast("缺少乘数");
                    return;
                }
                else if(l=='.')
                {
                    if(expresion.length()==1)
                    {
                        showToast("小数点使用错误");
                    }
                    else
                    {
                        char b = expresion.charAt(expresion.length()-2);
                        if(!Character.isDigit(b))
                        {
                            showToast("小数点使用错误");
                        }
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
                if(flag==1)
                {
                    textView.setText(expresion);
                    flag = 0;
                }
                if(expresion.equals(""))
                {
                    return;
                }
                char l = expresion.charAt(expresion.length()-1);
                if( l != '+' && l != '-' && l != '*' && l != '/' && l!='.' && l!='(')
                {
                    expresion = expresion + "/";
                    textView.setText(expresion);
                }
                else if( l == '-' &&expresion.length()>=2)
                {
                    char a = expresion.charAt(expresion.length()-2);
                    if(a == '+' || a == '-' ||a == '*' ||a == '/' || a=='(' || a==')')
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
                else if(l=='(')
                {
                    showToast("缺少被除数");
                    return;
                }
                else if(l=='.')
                {
                    if(expresion.length()==1)
                    {
                        showToast("小数点使用错误");
                    }
                    else
                    {
                        char b = expresion.charAt(expresion.length()-2);
                        if(!Character.isDigit(b))
                        {
                            showToast("小数点使用错误");
                        }
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
                if(flag==1)
                {
                    expresion = "";
                    textView.setText(expresion);
                    flag = 0;
                }

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
                if(flag==1)
                {
                    return;
                }

                if(expresion.length()==1)
                {
                    char a = expresion.charAt(0);
                    if(a=='-'||a=='.')
                        return;
                }

                if(count(expresion,'(')!=count(expresion,')'))
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("运算错误");
                    dialog.setMessage("括号不匹配！");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
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


                quwei();

                Count3();
//
                expresion = sizeyunsuan(expresion);
                if(expresion=="除数错误")
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("运算错误");
                    dialog.setMessage("0不能作为除数！");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
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

                flag = 1;
                textView.setText(expresion);

            }
        });

    }


    /*如果表达式尾+ - * / . ( 就去掉*/
    public void quwei()
    {
        char a;
        while((a = expresion.charAt(expresion.length()-1))=='+'||a=='-'||a=='*'||a=='/'||a=='('||a=='.')
        {
            expresion = expresion.substring(0,expresion.length()-1);
        }

    }

    public String Count1 (String expresion)
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
                if(i==0 || (i!=0 && (expresion.charAt(i-1)=='*'||expresion.charAt(i-1)=='/'||expresion.charAt(i-1)=='.')))
                {
                    break;
                }
                else if(expresion.charAt(i-1)=='E')
                {
                    continue;
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
            char c = expresion.charAt(j-1);
            if(j==expresion.length()-1)
            {
                break;
            }
            else if((b=='+'||b=='-'||b=='*'||b=='/')&&c!='E')
            {
                if(b=='-' && (expresion.charAt(j-1)=='*'||expresion.charAt(j-1)=='/'))
                {
                    continue;
                }
                j--;
                break;
            }
        }


        if(a=='*')
        {
            BigDecimal num1 = new BigDecimal(expresion.substring(i, n));
            BigDecimal num2 = new BigDecimal(expresion.substring(n + 1, j + 1));

            answer = "" + num1.multiply(num2);
        }
        else
        {
            if(Float.parseFloat(expresion.substring(n + 1, j + 1))==0)
            {
                return "除数错误";
            }
            else
            {

                BigDecimal num1 = BigDecimal.valueOf(Double.parseDouble(expresion.substring(i, n)));
                BigDecimal num2 = BigDecimal.valueOf(Double.parseDouble(expresion.substring(n + 1, j + 1)));

                answer = "" + num1.divide(num2,50, BigDecimal.ROUND_HALF_UP).doubleValue();
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

        expresion = expresionAns;
        return expresion;

    }





    /*处理连续符号*/
    public String fuhao(String expresion)
    {
        expresion = expresion.replaceAll("--","+");
        while(expresion.contains("++"))
        {
            expresion = expresion.replaceAll("\\u002B\\u002B", "+");
        }
        expresion = expresion.replaceAll("\\u002B-","-");

        return expresion;
    }




    public String Count2(String expresion)
    {
        int i,j;
        char a = '+';
        for(i=0; i<expresion.length(); i++)
        {
            a = expresion.charAt(i);
            if((a=='+'||a=='-')&&i!=0&&expresion.charAt(i-1)!='E')
            {
                break;
            }
        }
        for(j = i+1;j<expresion.length();j++)
        {
            char b = expresion.charAt(j);
            if(j==expresion.length()-1||((b=='+'||b=='-')&&expresion.charAt(i-1)!='E'))
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
            answer = num1.add(num2).toString();
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
        return expresion;
        //Toast.makeText(this,expresion,Toast.LENGTH_LONG);
    }


    /*判断有没有加减*/
    public boolean zuihou(String expresion)
    {
        for(int i=0;i<expresion.length();i++)
        {
            if(i!=0&&(expresion.charAt(i)=='+'||expresion.charAt(i)=='-')&&expresion.charAt(i-1)!='E')
            {
                return false;
            }
        }
        return true;
    }



    /*寻找字串运算*/
    public void Count3()
    {

        while(kuohao()) {
            /*寻找第一个最内括号下标*/
            int index1 = 0, index2 = 0;
            for (int i = 0; i < expresion.length(); i++) {
                char a = expresion.charAt(i);
                if (a == '(') {
                    index1 = i;
                } else if (a == ')') {
                    index2 = i;
                    break;
                }
            }

            String string = expresion.substring(index1 + 1, index2);
            String ans = sizeyunsuan(string);
            if (ans == "除数错误") {

                expresion = ans;
                return;
            }

            if (index1 == 0 && index2 == expresion.length() - 1) {
                expresion = ans;

            } else if (index1 == 0 && index2 != expresion.length() - 1) {
                char a = expresion.charAt(index2 + 1);
                if (Character.isDigit(a) || a == '.') {
                    expresion = ans + "*" + expresion.substring(index2 + 1, expresion.length());
                } else {
                    expresion = ans + expresion.substring(index2 + 1, expresion.length());
                }

            } else if (index1 != 0 && index2 == expresion.length() - 1) {
                char a = expresion.charAt(index1 - 1);
                if (Character.isDigit(a) || a == '.') {
                    expresion = expresion.substring(0, index1) + "*" + ans;
                } else {
                    expresion = expresion.substring(0, index1) + ans;
                }

            } else if (index1 != 0 && index2 != expresion.length() - 1) {
                char a = expresion.charAt(index1 - 1);
                char b = expresion.charAt(index2 + 1);
                if (Character.isDigit(a) || a == '.') {
                    ans = "*" + ans;
                }

                if (Character.isDigit(b) || b == '.') {
                    expresion = expresion.substring(0, index1) + ans + "*" + expresion.substring(index2 + 1, expresion.length());
                } else {
                    expresion = expresion.substring(0, index1) + ans + expresion.substring(index2 + 1, expresion.length());
                }

            }

        }

    }




    /*判断是否有括号*/
    public boolean kuohao()
    {
        for(int i = 0;i<expresion.length();i++)
        {
            char a = expresion.charAt(i);
            if(a=='('||a==')')
            {
                return true;
            }
        }
        return false;
    }


    /*判断是否有乘除*/
    public boolean chengchu(String expresion)
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





    public String sizeyunsuan(String expresion) {

        while (chengchu(expresion))
        {
            expresion = Count1(expresion);
            if (expresion == "除数错误")
            {
                return expresion;
            }
            expresion = fuhao(expresion);
        }


        while (!zuihou(expresion))
        {
            expresion = Count2(expresion);
            expresion = fuhao(expresion);
        }

        return expresion;
    }


    public void showToast(String string)
    {
        if(mToast==null)
        {
            mToast = Toast.makeText(this, string, Toast.LENGTH_SHORT);
            mToast.show();
        }
        else
        {
            mToast.setText(string);
            mToast.show();
        }
    }


    public int count(String string, char a)
    {
        int count=0;
        for(int i = 0; i<string.length(); i++)
        {
            if(string.charAt(i)==a)
            {
                count++;
            }
        }
        return count;
    }


}
