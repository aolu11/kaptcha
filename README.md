# Kaptcha

kaptcha - A configuable kaptcha generation engine clone of http://code.google.com/p/kaptcha/.

## Example

```java
// Usage
@Autowired private Kaptcha kaptcha;

kaptcha.captcha(request, response)

code.equals(kaptcha.getGenerateKey(request))

// Config
@Configuration
public class CaptchaConfig {

    @Bean
    public Kaptcha getKaptcha(){
        Properties properties = new Properties();
        properties.setProperty(Constants.KAPTCHA_BORDER, "no");
        Config config = new Config(properties);
        return new Kaptcha(config);
    }
}

```

