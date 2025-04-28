package grid;

import java.util.HashMap;

public class test
{

  public static void main(String[] args)
  {
    GridTuple<Double, Double> one = new GridTuple<>(3.0, 4.9);
    GridTuple<Double, Double> two = new GridTuple<>(3.00000001, 4.9);
    HashMap<GridTuple<Double, Double>, String> grid = new HashMap<>();
    grid.put(one, "different");
    grid.put(two, "same");
    System.out.println(one.equals(two));
    System.out.println(grid.get(one));
    System.out.println(grid.get(two));
    System.out.println(grid.keySet().size());
  }

}
