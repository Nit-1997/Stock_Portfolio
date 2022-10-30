import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
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

  public static int checker(String num, Set<String> set){
    int count=0;
    for(int i=0;i<num.length();i++){
      for(int n=0;n<=9;n++){
        String newNum = num.substring(0,i)+n+num.substring(i+1);
        if(set.contains(newNum)) count++;
      }
    }

    return count;
  }

  public static int func5(int[] nums){
    int count=0;

      Set<String> set = new HashSet<>();
      for(int num : nums) set.add(num+"");
      for(int num : nums){
        count+=checker(num+"",set);
      }
      return count;
  }

  public static void main(String[] args) {
    int[] arr = {104, 104, 104, 102,108, 100, 108, 101, 101, 100, 103, 104, 105, 107, 100, 109, 110, 106, 107, 107};
//    int[] arr = {111,161,211,261,311,361,411,461,511,561,611,661,711,761,811,861,911,961,1011,1061};
    System.out.println(func5(arr));
  }

}
