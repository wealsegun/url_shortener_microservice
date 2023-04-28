package com.example.url_shortener.urlShortener;

import com.example.url_shortener.url.Shortener;
import com.example.url_shortener.url.URLShortenerRepository;
import com.example.url_shortener.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class UrlShortenerService {
    private final UserRepository repository;
    private final URLShortenerRepository urlRepository;
    private static final String BITLY_ACCESS_TOKEN = "63b1526d98e86a26ea1cb06123d33cd2d43eb5b8"; // Replace with your Bitly access token

    public  Shortener getShortenedUrlById(String userEmail, Integer id) {
        repository.findByEmail(userEmail).orElseThrow();
        return urlRepository.getShortenerById(id);
    }

    public List<Shortener> getShortenedUrlsByUserEmail(String userEmail) {
        repository.findByEmail(userEmail).orElseThrow();
        return urlRepository.getShortenerByUserEmail(userEmail);
    }
    public  boolean createUserShortenedUrl(UrlUserRequest request) {
        long currentTimeMillis = System.currentTimeMillis();
        long expiryTimeMillis = currentTimeMillis + (24 * 60 * 60 * 1000); // add 24 hours in milliseconds
        repository.findByEmail(request.getUserEmail()).orElseThrow();
        Shortener shortened;
        if (request.isCustomRequested()) {
            shortened = Shortener.builder()
                    .urlName(request.getUrlName())
                    .longUrl(request.getLongUrl())
                    .tinyUrl(shortenUrl(request.getLongUrl()))
                    .shortenedBitlyUrl(shortenUrlWithBitly(request.getLongUrl()))
                    .userEmail(request.getUserEmail())
                    .isCustomRequested(request.isCustomRequested())
                    .customUrl(customizedUrl(request.getLongUrl()))
                    .createdDate(new Date(currentTimeMillis))
                    .expiryDate(new Date(expiryTimeMillis))
                    .build();
        } else {
            shortened = Shortener.builder()
                    .urlName(request.getUrlName())
                    .longUrl(request.getLongUrl())
                    .tinyUrl(shortenUrl(request.getLongUrl()))
                    .shortenedBitlyUrl(shortenUrlWithBitly(request.getLongUrl()))
                    .userEmail(request.getUserEmail())
                    .isCustomRequested(request.isCustomRequested())
                    .customUrl(null)
                    .createdDate(new Date(currentTimeMillis))
                    .expiryDate(new Date(expiryTimeMillis))
                    .build();
        }
        urlRepository.save(shortened);
        return true;
    }
    public String shortenUrl(String longUrl) {
        String tinyUrl = "";
        try {
            URL url = new URL("http://tinyurl.com/api-create.php?url=" + longUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                Scanner scanner = new Scanner(connection.getInputStream());
                while (scanner.hasNext()) {
                    tinyUrl += scanner.next();
                }
                scanner.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tinyUrl;
    }
//    public String shortenUrlWithBitly(String longUrl) {
//        String shortUrl = "";
//        try {
//            URL url = new URL("https://api-ssl.bitly.com/v4/shorten");
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("POST");
//            connection.setRequestProperty("Content-Type", "application/json");
//            connection.setRequestProperty("Authorization", "Bearer " + BITLY_ACCESS_TOKEN);
//            connection.setDoOutput(true);
//            String requestBody = "{\"long_url\":\"" + longUrl + "\", \"domain\": \"bit.ly\", \"o_7kt4avusif\": \"Ba1bc23dE4F\"}";
////            String requestBody = "{\"long_url\":\"" + longUrl + "\" + \"domain\": \"bit.ly\"+ \"group_guid\": \"o_7kt4avusif\"+ \"title\": \"Bitly API Documentation\"}";
//            connection.getOutputStream().write(requestBody.getBytes());
//            connection.connect();
//            int responseCode = connection.getResponseCode();
//            if (responseCode == 200) {
//                Scanner scanner = new Scanner(connection.getInputStream());
//                while (scanner.hasNext()) {
//                    shortUrl += scanner.next();
//                }
//                scanner.close();
//            }
//            else {
//                Scanner scanner = new Scanner(connection.getErrorStream());
//                while (scanner.hasNext()) {
//                    System.out.println(scanner.next());
//                }
//                scanner.close();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return shortUrl;
//    }
public String shortenUrlWithBitly(String longUrl) {
    String shortUrl = "";
    try {
        URL url = new URL("https://api-ssl.bitly.com/v4/shorten");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + BITLY_ACCESS_TOKEN);
        connection.setDoOutput(true);
//        String requestBody = "{\"long_url\":\"" + longUrl + "\", \"domain\": \"bit.ly\", \"o_7kt4avusif\": \"Ba1bc23dE4F\"}";
//        String requestBody = "{\"long_url\":\"" + longUrl + "\"}";

        String requestBody = "{\"o_7kt4avusif\": \"o_7kt4avusif\", \"domain\": \"bit.ly\", \"long_url\": \""+longUrl+ "\" }";
        connection.getOutputStream().write(requestBody.getBytes());
        connection.connect();
        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNext()) {
                shortUrl += scanner.next();
            }
            scanner.close();
        }
        else {
            Scanner scanner = new Scanner(connection.getErrorStream());
            while (scanner.hasNext()) {
                System.out.println(scanner.next());
            }
            scanner.close();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return shortUrl;
}

    public String customizedUrl(String longUrl) {
        return longUrl;
    }
}