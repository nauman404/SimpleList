# SimpleList

### 1. What is the major difference between an ​ abstract ​class and an ​ interface ?

Main difference is methods of a Java interface are implicitly abstract and cannot have implementations. A Java abstract class can have instance methods that implements a default behavior.
```java
Example:
//abstract class
abstract class Sum{
   //abstract methods
   public abstract int SumOfTwo(int n1, int n2);
   
   //Regular method with implementation
   public void disp(){
      System.out.println("Method of class Sum");
   }
}

class AbstractDemo extends Sum{
   public int SumOfTwo(int num1, int num2){
	return num1+num2;
   }
   public static void main(String args[]){
      AbstractDemo obj = new AbstractDemo();
      System.out.println(obj.SumOfTwo(3, 7));
      obj.disp();
   }
}
```
Output:10
Method of class Sum
```java
interface SumInterface
{
   /* All the methods are public abstract by default
    * Note down that these methods are not having body
    */
  
   public int SumOfTwo(int n1, int n2);
   public void disp();
}

class DemoInterface implements SumInterface
{
  public int SumOfTwo(int num1, int num2){
	return num1+num2;
   }
   
   public void disp(){
      System.out.println("Method of class Sum");
   }
   
  public static void main(String arg[])
  {
      SumInterface obj = new DemoInterface();
      System.out.println(obj.SumOfTwo(3, 7));
      obj.disp();
  }
}
```
Output:10
Method of class Sum

 
### 2. Why is Java 7’s class inheritance flawed?
I don’t know.

### 3. What are the major differences between ​ Activities ​and ​ Fragments ​? 

- Fragment is a part of an activity, which contributes its own UI to that activity. Fragment can be thought like a sub activity, where as the complete screen with which user interacts is called as activity. An activity can contain multiple fragments. Fragment are mostly a part of an activity.

- Sometimes context is required for access or something that is specific to an activity you will need to get a reference to the super activity of the fragment, e.g. while creating an intent inside an activity you do something like this :
    Intent intent = new Intent(this,SomeActivity.class);
but inside a fragment you will have to do something like this:
    Intent intent = new Intent(super.getActivity(),SomeActivity.class);
	
- An Activity may contain 0 or multiple number of fragments based on the screen size. A fragment can be reused in multiple activities, so it acts like a reusable component in activities.

- A fragment can't exist independently. It should be always part of an activity whereas activity can exist without any fragment in it.

- There is also a difference in lifecycle between an activity and a fragment is how one is stored in its respective back stack. An activity is placed into a back stack of activities that's managed by the system when it's stopped, by default (so that the user can navigate back to it with the Back button). However, a fragment is placed into a back stack managed by the host activity only when you explicitly request that the instance be saved by calling addToBackStack() during a transaction that removes the fragment.

### 4. When using Fragments, how do you communicate back to their hosting Activity ​? 
There are many ways to communicate hosting activity like getting activity reference and call public method etc, But best way is using an interface as a call back to the activity. The point of using interfaces in java is mainly flexibility in implementation. It enables you to reuse fragments (the main point of having fragments in the first place). If you create a method in the activity, you cannot reuse it when you want to do something different with a given fragment.

Here is an example of Fragment to Activity communication:
```java
MainActivity.java
/**
 * The main activity that communicates with the fragment(s)
 * 
 */
 
 public class MainActivity extends Activity implements NewsItemsFragment.OnNewsItemSelectedListener
 {
    private NewsItemsFragment mItemsFragment;
 
    @Override
    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
 
        setContentView(R.layout.main);
 
        //Instantiate some stuff here like view components
        mItemsFragment = new NewsItemsFragment();
 
        //Now you can set the fragment to be visible here
        setFragment(mItemsFragment);
    }
 
    public void setFragment(Fragment frag)
    {    
         FragmentManager fm = getFragmentManager();   
         if (fm.findFragmentById(R.id.container) == null) {
            fm.beginTransaction().add(R.id.container, frag).commit();
        }
     
    }
 
    //Override the method here
    @Override
    public void onNewsItemPicked(int position)
    {
        //Do something with the position value passed back
        Toast.makeText(activity, "Clicked "+ position, Toast.LENGTH_LONG).show();
        
    }
 }

```
  
 ```java
 /**
 * Fragment that inflates a list view to display news headlines
 * 
 */
 
 public class NewsItemsFragment extends ListFragment implements AdapterView.OnItemClickListener
 {
    Activity activity;
    public static List<string> newsHeadlines = new ArrayList</string><string>(){{
        add("Android Is Great");
        add("Learn How To Program On Your Own");
        add("The next big thing is here - your awesome app!");
    }};
    
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        this.activity = activity;
    }
 
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
 
        ArrayAdapter</string><string> adapter = new ArrayAdapter</string><string>(activity, R.layout.simple_row_item, newsHeadlines);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
 
 
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.head_lines_fragment, container, false);
 
 
    }
 
    @Override
    public void onItemClick(AdapterView< ?> parent, View view, int position, long id) {
        Toast.makeText(activity, "Clicked "+ position, Toast.LENGTH_LONG).show();
 
        try{
            ((OnNewsItemSelectedListener) activity).onNewsItemPicked(position);
        }catch (ClassCastException cce){
 
        }
    }
 
    public interface OnNewsItemSelectedListener{
        public void onNewsItemPicked(int position);
    }
 }
 ```
 
### 5. Can you make an entire app without ever using ​ Fragments? Why or why not? Are there any special cases when you absolutely have to use or should use Fragments?

Yes, Entire app can be made without using fragments.
Why?
Activity is a part of the four core components of Android, along with Service, Broadcast Receiver and Content provider. But fragment is not a core component, it's there to be used as a part or 'fragment' of activity. Sp app certainly can be built using only activities without fragments.

Are there any special cases when you absolutely have to use or should use Fragments?
Use fragment when you have to change the UI components of application to significantly improve app response time.
Tablet Support - Multipane developments
Fragments are the modern way to develop Android apps. For supporting multiple different display types (phone, tablet) you must use fragments.
Graphic Oriented App:
Activity keep its view in memory even on backstack while fragments dont keep views in backstack. So it is recommended to use fragments when using graphic oriented app.


### 6. What makes an ​ AsyncTask ​ such an annoyance to Android developers? Detail some of the issues with ​ AsyncTask ​ , and how to potentially solve them

Below are some of the problems that make android developers annoy when using AsyncTask and their possible solutions.

For Short Operations
AsyncTasks should ideally be used for short operations (a few seconds at the most.) If you need to keep threads running for long periods of time, it is highly recommended you use the various APIs provided by the java.util.concurrent package such as Executor, ThreadPoolExecutor and FutureTask.

Screen Rotation
When an app is rotated, the entire Activity is destroyed and recreated. When the Activity is restarted, your AsyncTask’s reference to the Activity is invalid, so onPostExecute() will have no effect on the new Activity. This can be confusing if you are implicitly referencing the current Activity by having AsyncTask as an inner class of the Activity.

The usual solution to this problem is to hold onto a reference to AsyncTask that lasts between configuration changes, which updates the target Activity as it restarts. There are a variety of ways to do this, though they either boil down to using a global holder (such as in the Application object) or passing it through Activity.onRetainNonConfigurationInstance(). For a Fragment-based system, you could use a retained Fragment (via Fragment.setRetainedInstance(true)) to store running AsyncTasks.

