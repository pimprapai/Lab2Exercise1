package th.ac.tu.siit.its333.lab2exercise1;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    // expr = the current string to be calculated
    StringBuffer expr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expr = new StringBuffer();
        updateExprDisplay();
    }

    public void updateExprDisplay() {
        TextView tvExpr = (TextView)findViewById(R.id.tvExpr);
        tvExpr.setText(expr.toString());
    }

    public void recalculate() {
        int result =0;
        //Calculate the expression and display the output

        //Split expr into numbers and operators
        //e.g. 123+45/3 --> ["123", "+", "45", "/", "3"]
        //reference: http://stackoverflow.com/questions/2206378/how-to-split-a-string-but-also-keep-the-delimiters

        String e = expr.toString();
        String[] tokens = e.split("((?<=\\+)|(?=\\+))|((?<=\\-)|(?=\\-))|((?<=\\*)|(?=\\*))|((?<=/)|(?=/))");

        char operand = ' ';
        for(int i=0;i<tokens.length;i++)
        {
            char op = tokens[i].charAt(0);
            if(isOperand(op))
            {
                operand = op;

            }
            else if(!isOperand(op))
            {
                if(operand=='+')
                {
                    result+=Integer.parseInt(tokens[i]);
                }
                else if(operand=='-')
                {
                    result-=Integer.parseInt(tokens[i]);
                }
                else if(operand=='*')
                {
                    result*=Integer.parseInt(tokens[i]);
                }
                else if(operand=='/')
                {
                    result/=Integer.parseInt(tokens[i]);
                }
                else if(operand==' ')
                {
                    result=Integer.parseInt(tokens[i]);
                }
            }
        }
        TextView tvAns = (TextView)findViewById(R.id.tvAns);
        tvAns.setText(Integer.toString(result));

    }

    public void digitClicked(View v) {
        //d = the label of the digit button
        String d = ((TextView)v).getText().toString();
        //append the clicked digit to expr
        expr.append(d);
        //update tvExpr
        updateExprDisplay();
        //calculate the result if possible and update tvAns
        recalculate();
    }

    public void operatorClicked(View v) {
        String d = ((TextView)v).getText().toString();
        //IF the last character in expr is not an operator and expr is not "",
        if(!expr.equals("")&& !isOperand(expr.charAt(expr.length()-1)))
        {
            expr.append(d);
            updateExprDisplay();
        }


        //THEN append the clicked operator and updateExprDisplay,
        //ELSE do nothing
    }

    private boolean isOperand(char c){
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    public void ACClicked(View v) {
        //Clear expr and updateExprDisplay
        expr = new StringBuffer();
        updateExprDisplay();
        //Display a toast that the value is cleared
        Toast t = Toast.makeText(this.getApplicationContext(),
                "All cleared", Toast.LENGTH_SHORT);
        t.show();

        TextView tvAns = (TextView)findViewById(R.id.tvAns);
        tvAns.setText("");
    }

    public void BSClicked(View v) {
        //Remove the last character from expr, and updateExprDisplay
        if (expr.length() > 0) {
            expr.deleteCharAt(expr.length()-1);
            recalculate();
            updateExprDisplay();
        }
    }

    public void equalClicked(View v) {
        //Remove the last character from expr, and updateExprDisplay
        //TextView tvExpr = (TextView)findViewById(R.id.tvExpr);
        TextView tvAns = (TextView)findViewById(R.id.tvAns);
        String s = tvAns.getText().toString();
        tvAns.setText("0");

        expr = new StringBuffer();
        expr.append(s);
        updateExprDisplay();

    }

    public int mem=0;
    public void mClicked(View v) {
        TextView tvAns = (TextView)findViewById(R.id.tvAns);

        if(v==(TextView)findViewById(R.id.mc))
        {
            mem=0;
            Toast t = Toast.makeText(this.getApplicationContext(),
                    "Memory is cleared", Toast.LENGTH_SHORT);
            t.show();
         }
        else if(v==(TextView)findViewById(R.id.mr))
        {
            expr = new StringBuffer();
            expr.append(Integer.toString(mem));
            updateExprDisplay();

            tvAns.setText(Integer.toString(mem));

            Toast t = Toast.makeText(this.getApplicationContext(),
                    "Memory is "+mem, Toast.LENGTH_SHORT);
            t.show();
        }
        else if(v==(TextView)findViewById(R.id.madd))
        {
            mem+=Integer.parseInt(tvAns.getText().toString());


            Toast t = Toast.makeText(this.getApplicationContext(),
                    "Memory is added,result="+mem, Toast.LENGTH_SHORT);
            t.show();
        }
        else if(v==(TextView)findViewById(R.id.msub))
        {
            mem-=Integer.parseInt(tvAns.getText().toString());


            Toast t = Toast.makeText(this.getApplicationContext(),
                    "Memory is subtracted,result="+mem, Toast.LENGTH_SHORT);
            t.show();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
