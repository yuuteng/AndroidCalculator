package yuut.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button btn_0;
    Button btn_1;
    Button btn_2;
    Button btn_3;
    Button btn_4;
    Button btn_5;
    Button btn_6;
    Button btn_7;
    Button btn_8;
    Button btn_9;
    Button btn_point;//小数点
    Button btn_clear;//清除
    Button btn_del;//删除
    Button btn_add;//加
    Button btn_minus;//减
    Button btn_multi;//乘
    Button btn_divide;//除
    Button btn_equal;//等于
    EditText et_input;//显示框
    boolean clear_flag;//清空标识
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //实例化按钮和显示框
        btn_0 = (Button) findViewById(R.id.btn_0);
        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_2 = (Button) findViewById(R.id.btn_2);
        btn_3 = (Button) findViewById(R.id.btn_3);
        btn_4 = (Button) findViewById(R.id.btn_4);
        btn_5 = (Button) findViewById(R.id.btn_5);
        btn_6 = (Button) findViewById(R.id.btn_6);
        btn_7 = (Button) findViewById(R.id.btn_7);
        btn_8 = (Button) findViewById(R.id.btn_8);
        btn_9 = (Button) findViewById(R.id.btn_9);
        btn_point = (Button) findViewById(R.id.btn_point);
        btn_clear = (Button) findViewById(R.id.btn_clear);
        btn_del = (Button) findViewById(R.id.btn_del);
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_minus = (Button) findViewById(R.id.btn_minus);
        btn_multi = (Button) findViewById(R.id.btn_multi);
        btn_divide = (Button) findViewById(R.id.btn_divide);
        btn_equal = (Button) findViewById(R.id.btn_equal);
        et_input = (EditText) findViewById(R.id.ed_input);

        //设置点击事件
        MyButtonListener listener = new MyButtonListener();
        btn_0.setOnClickListener(listener);
        btn_1.setOnClickListener(listener);
        btn_2.setOnClickListener(listener);
        btn_3.setOnClickListener(listener);
        btn_4.setOnClickListener(listener);
        btn_5.setOnClickListener(listener);
        btn_6.setOnClickListener(listener);
        btn_7.setOnClickListener(listener);
        btn_8.setOnClickListener(listener);
        btn_9.setOnClickListener(listener);
        btn_point.setOnClickListener(listener);
        btn_clear.setOnClickListener(listener);
        btn_del.setOnClickListener(listener);
        btn_add.setOnClickListener(listener);
        btn_minus.setOnClickListener(listener);
        btn_multi.setOnClickListener(listener);
        btn_divide.setOnClickListener(listener);
        btn_equal.setOnClickListener(listener);
    }
   class MyButtonListener implements View.OnClickListener{
       @Override
       public void onClick(View v) {
           String str= et_input.getText().toString();
           switch (v.getId()){
               case R.id.btn_0:
               case R.id.btn_1:
               case R.id.btn_2:
               case R.id.btn_3:
               case R.id.btn_4:
               case R.id.btn_5:
               case R.id.btn_6:
               case R.id.btn_7:
               case R.id.btn_8:
               case R.id.btn_9:
                   //如果是运算结果,再点击数字则清空
                   if(clear_flag){
                       clear_flag=false;
                       str="";
                       et_input.setText("");
                   }
                   //如果不以0开头 或者 长度>1 则将按钮数字加入到str中
                   if(!str.startsWith("0")||str.length()>1)
                       et_input.setText(str + ((Button)v).getText());
                   //如果本来是0,又点了一个不是0的数字则修改显示
                   else if(str.startsWith("0") && v.getId()!=R.id.btn_0)
                       et_input.setText(((Button)v).getText());
                   break;
               case R.id.btn_point:
                   //如果是运算结果,再点击point则清空
                   if(clear_flag){
                       clear_flag=false;
                       et_input.setText("");
                   }
                   //如果str中已经有了运算符,截取字符串s2,判断是否已含point
                   //如果str没有运算符,则直接判断是否已经输入过 point
                   if(str.contains(" ")){
                       String s2 = str.substring(str.indexOf(" ")+2);
                       if(!s2.contains("."))
                           et_input.setText(str + ((Button)v).getText());
                   }else if(!str.contains(".")){
                       et_input.setText(str + ((Button)v).getText());
                   }
                   break;
               case R.id.btn_add:
               case R.id.btn_minus:
               case R.id.btn_multi:
               case R.id.btn_divide:
                   //如果是运算结果,再点击point则清空
                   if(clear_flag){
                       clear_flag=false;
                   }
                   if(!str.contains(" "))
                       et_input.setText(str +" "+ ((Button)v).getText() + " ");
                   break;
               case R.id.btn_clear:
                   clear_flag=false;
                   et_input.setText("");
                   break;
               case R.id.btn_del:
                   //截取length-1长度的字符串,删掉最后一位
                   if(str!=null && !str.equals("")){
                       et_input.setText(str.substring(0,str.length()-1));
                   }
                   break;
               case R.id.btn_equal:
                   getResult();
                   break;
           }
       }
   }

    //运算S
    private void getResult(){
        String exp = et_input.getText().toString();
        //什么都没输入
        if (exp==null || exp.equals(""))
            return;
        //判断有没有进行运算,点击数字直接点等号的情况
        if (!exp.contains(" ")){
            return;
        }
        if(clear_flag){
            clear_flag=false;
            return;
        }
        clear_flag=true;
        double result = 0;
        String s1 = exp.substring(0,exp.indexOf(" "));//运算符前面的字符串
        String op = exp.substring(exp.indexOf(" ")+1,exp.indexOf(" ")+2);//运算符
        String s2 = exp.substring(exp.indexOf(" ")+3);//op后面的字符串
        //s1 op s2
        if (!s1.equals("") && !s2.equals("")){
            double d1 = Double.parseDouble(s1);
            double d2 = Double.parseDouble(s2);
            //运算
            if(op.equals("+")){
                result = d1+d2;
            }else if (op.equals("-")){
                result=d1-d2;
            }else if (op.equals("×")){
                result=d1*d2;
            }else {
                if (d2==0)
                    result=0;
                else
                    result=d1/d2;
            }
            String resultstr = result+"";
            //截取double小数点后面部分,判断长度,如果 小数点后面只有0则转化为int输出
            String endstr = resultstr.substring(resultstr.indexOf(".")+1);
            if(endstr.equals("0")){
                int rs = (int)result;
                et_input.setText(rs+"");
            }
            else {
                et_input.setText(result + "");
            }
        }
        //op s2
        else if(s1.equals("") && !s2.equals("")){
            double d2 = Double.parseDouble(s2);
            //运算
            if(op.equals("+")){
                result = d2;
            }else if (op.equals("-")){
                result=0-d2;
            }else if (op.equals("×")){
                result=0;
            }else {
                result=0;
            }
            int rs = (int)result;
            et_input.setText(rs+"");
        }
        //s1 op
        else if(!s1.equals("") && s2.equals("")){
            et_input.setText(exp);
        }
    }
}
