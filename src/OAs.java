import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class OAs {

  private static class Pair{
    int x;
    int y;
    Pair(int x, int y){
      this.x=x;
      this.y=y;
    }
  }
  public static boolean[] func1(int[][] operations){
    List<Boolean> res = new ArrayList<>();
    Set<Pair> options = new HashSet<>();
    for(int i=0;i<operations.length;i++){
      if(operations[i][0]==0){
        options.add(new Pair(operations[i][1],operations[i][2]));
      }
      else{
        boolean val=true;
        for(Pair option : options){
          if((option.x<=operations[i][1] && option.y<=operations[i][2]) || option.x<=operations[i][2] && option.y<=operations[i][1]) continue;
          else {
            val=false;
            break;
          }
        }
        res.add(val);
      }
    }
    boolean[] arr = new boolean[res.size()];
    for(int i=0;i<res.size();i++)arr[i]=res.get(i);
    return arr;
  }


  public static boolean uniqueCharacters(String s){
    boolean val=true;
    for(int i=1;i<s.length();i++){
      if(s.charAt(i)==s.charAt(i-1)) val=false;
    }
    return val;
  }
  public static String func2(String str){
    if(uniqueCharacters(str)) return str;
    Queue<Integer> queue = new ArrayDeque<>();
    Set<Integer> set = new HashSet<>();
    for(int i=0;i<str.length();i++){
      int num=str.charAt(i)-'0';
      if(!set.contains(num)){
        set=new HashSet<>();
        set.add(num);
        queue.add(num);
      }
    }
    StringBuilder sb = new StringBuilder();
    int sum=0;
    for(int i=0;i<str.length();i++){
      int num=str.charAt(i)-'0';
      if(queue.peek()==num){
        sum+=num;
      }
      else{
        sb.append(sum);
        sum=0;
        queue.remove();
        i--;
      }
    }
    sb.append(sum);
    return func2(String.valueOf(sb));
  }


  public static int getMaximumRewardPoints(int k, List<Integer> reward_1, List<Integer> reward_2){
    int n=reward_2.size();
    return -1;
  }

}
