@RestController
public class HelloController{
    @GetMapping(value="/hello")
    public String sayHello(){
        System.out.println("hello world!");
    }
}
