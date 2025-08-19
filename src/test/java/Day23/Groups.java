package Day23;

import org.testng.annotations.Test;

public class Groups {
	
	@Test(groups = {"smoke", "sanity"})
	public void Test1() {
		System.out.println("This is smoke + sanity");
	}
	
	@Test(groups = {"smoke"})
	public void Test2() {
		System.out.println("This is smoke");
	}
	
	@Test(groups = {"smoke", "regression"})
	public void Test3() {
		System.out.println("This is smoke + regression ");
	}
	
	@Test(groups = {"sanity"})
	public void Test4() {
		System.out.println("This is sanity");
	}
	
	@Test(groups = {"regression"})
	public void Test5() {
		System.out.println("This is regression");
	}
	
	@Test(groups = {"regression", "sanity"})
	public void Test6() {
		System.out.println("This is regression + sanity");
	}
	
}
