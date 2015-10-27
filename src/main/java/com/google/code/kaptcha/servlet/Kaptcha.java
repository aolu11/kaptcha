package com.google.code.kaptcha.servlet;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;

public class Kaptcha {

	private Producer kaptchaProducer = null;
	private String sessionKeyValue = null;
	private String sessionKeyDateValue = null;

    public Kaptcha() {
        this(new Config(new Properties()));
    }

	public Kaptcha(Config config) {
		ImageIO.setUseCache(false);
		kaptchaProducer = config.getProducerImpl();
		sessionKeyValue = config.getSessionKey();
		sessionKeyDateValue = config.getSessionDate();
	}

	public void captcha(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setHeader("Cache-Control", "no-store, no-cache");
		resp.setContentType("image/jpeg");
		String capText = this.kaptchaProducer.createText();
		BufferedImage bi = this.kaptchaProducer.createImage(capText);
		ServletOutputStream out = resp.getOutputStream();
		ImageIO.write(bi, "jpg", out);
		req.getSession().setAttribute(this.sessionKeyValue, capText);
		req.getSession().setAttribute(this.sessionKeyDateValue, new Date());
	}

	public String getGeneratedKey(HttpServletRequest req) {
		HttpSession session = req.getSession();
		return (String) session
				.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
	}
}
