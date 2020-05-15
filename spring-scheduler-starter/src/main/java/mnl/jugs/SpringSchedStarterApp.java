package mnl.jugs;

import java.util.Date;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
public class SpringSchedStarterApp {

	public static void main(String[] args) {
		SpringApplication.run(SpringSchedStarterApp.class, args);
	}

	/**
	 * 05/15/2020 ECQ 
	 * Jose Roy G. Javelosa
	 * 
	 * I.
	 */
	@Scheduled(fixedDelay = 2000L)
	void startProcessOne() {
		System.out.println("**APP** fixedDelay Started " + new Date());
	}

	/**
	 * II.
	 * 
	 * param : fixedDelayString enables to command the scheduler to run on specific
	 * intervals mins hours in readable String format vs milliseconds 
	 * "PT10S <-- run every 10 seconds 
	 * "PT5M  <-- run every 5 minutes 
	 * "PT1H  <-- run every 1 hour
	 */
	@Scheduled(fixedDelayString = "PT1M")
	void startFixedDelayString() {
		System.out.println("**APP** fixedDelayString Started " + new Date());
	}

	/**
	 * III.
	 * 
	 * @fixedRate execution will NOT overlap but will execute immediately after the currently
	 * running instance of the method is finished 
	 *  
	 * @initialDelay The task will be
	 * executed a first time after the initialDelay value – and it will continue to
	 * be executed according to the fixedDelay.
	 */
	private static int i = 0;

	@Scheduled(initialDelay = 1000, fixedRate = 1000)
	public void startfixedRateWithInitialDelay() throws InterruptedException {
		System.out.println("**APP** Started  : fixedRate Process # " + ++i + " " + new Date());
		Thread.sleep(5000);
		System.out.println("**APP** Finished : fixedRate Process # " + i + " " + new Date());
	}
	
	/**
	 * IV.
	 * 
	 * You can set the delay, rate values in an external properties file
	 * by using Spring Expressions "${x.y.z}"
	 */
	@Scheduled(fixedDelayString = "${sched.delay.rule1}")
	void startFixedDelayStringByPropertiesFile() {
		System.out.println("**APP** fixedDelayStringByPropertiesFile Started " + new Date());
	}
	
	/**
	 * V.
	 * 
	 * you can use cron expressions for complex detailed schedules and timings
	 * "* * * * * *" = seconds minutes hour dayOfTheMonth month dayOfTheWeek	 * 
	 * startCronSchedule method below will execute every 15mins, between 8am - 5pm, on weekdays only  
	 *        
	 */			
			
	@Scheduled(cron = "0 */15 8-17 * * MON-FRI")
	void startCronSchedule() {
		System.out.println("**APP** cronSchedule Started " + new Date());
	}
	
	/**
	 * VI.
	 * 
	 * you can also use data from properties file for the cron expression
	 * You can use as cron resources : https://crontab.guru/
	 */
	@Scheduled(cron = "${sched.cron.weekend1}")
	void startCronScheduleFromPropertiesFile() {
		System.out.println("**APP** cronScheduleByPropertiesFile Started " + new Date());
	}

}

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)
class SchedulingConfiguration {

}