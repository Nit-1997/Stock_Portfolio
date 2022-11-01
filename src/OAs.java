import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class OAs {

  public static void func1(String num){
    String reversed = new StringBuilder(num).reverse().toString();
    System.out.println(reversed);
    StringBuilder res = new StringBuilder();
    for(int i=0;i<reversed.length()-2;i++){
      if(i+1<reversed.length()){
        int num2 = Integer.parseInt(reversed.substring(i,i+2));
        if((num2>=65 && num2<=90) || num2==32 || (num2>=97 && num2<=99)){
          res.append((char)num2);
          i++;
        }
      }
      else if(i+2<reversed.length()){
        int num3 = Integer.parseInt(reversed.substring(i,i+3));
        if(num3>=100 && num3<=122){
          res.append((char)num3);
          i+=2;
        }
      }
      System.out.println(res+" "+i);
    }
    System.out.println(res);
  }

  public static void main(String[] args) {
      func1("23511011501782351112179911801562340161171141148");
  }

}
