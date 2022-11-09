import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class oa {

  public static String getApiData(String baseUrl) {
    URL url = null;
    try {
      url = new URL(baseUrl);
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphaVantage API has either changed or "
              + "no longer works");
    }
    InputStream in = null;
    StringBuilder output = new StringBuilder();

    try {
      in = url.openStream();
      int b;

      while ((b = in.read()) != -1) {
        output.append((char) b);
      }
      return output.toString();
    } catch (IOException e) {
      throw new IllegalArgumentException("No data");
    }
  }

  public static List<Integer> bodyTemperature(String doctorName, int diagnosisId) {
    List<Integer> ans = new ArrayList<>();

    String a = "https://jsonmock.hackerrank.com/api/medical_records";
    String b = getApiData(a);
    String[] c = b.split(",");
    String pageStr = c[3];
    String[] broken = pageStr.split(":");
    int pages = Integer.parseInt(broken[1]);

    for (int j = 1; j <= pages; j++) {
      String url = "https://jsonmock.hackerrank.com/api/medical_records?page="+j;
      String out = getApiData(url);
      String[] outp = out.split(",");
      for (int i = 0; i < outp.length; i++) {
        if (outp[i].contains("\"diagnosis\":{\"id\":" + diagnosisId)) {
          if (outp[i + 9].contains(doctorName)) {
            String[] bodyTempString = outp[i + 7].split(":");
            int currTemp = (int) Double.parseDouble(bodyTempString[1].substring(0, bodyTempString[1].length() - 1));
            ans.add(currTemp);
          }
        }
      }
    }

    Collections.sort(ans);
    List<Integer> finalAns = new ArrayList<>();
    finalAns.add(ans.get(0));
    finalAns.add(ans.get(ans.size() - 1));
    return finalAns;
  }

  public static void main(String[] args) {
    List<Integer> ans = bodyTemperature("Dr Allysa Ellis", 2);
    for (int i : ans) {
      System.out.println(i);
    }
  }
}
