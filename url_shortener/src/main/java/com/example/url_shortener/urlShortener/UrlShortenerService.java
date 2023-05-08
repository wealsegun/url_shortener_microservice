package com.example.url_shortener.urlShortener;

import com.example.url_shortener.url.ShortCount;
import com.example.url_shortener.url.Shortener;
import com.example.url_shortener.url.URLShortenerRepository;
import com.example.url_shortener.user.UserRepository;
import fr.plaisance.bitly.Bitly;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.MediaType;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static fr.plaisance.bitly.Bit.ly;

@Service
@RequiredArgsConstructor
public class UrlShortenerService {
    private final UserRepository repository;
    private final URLShortenerRepository urlRepository;
    private final ShortUrlervice shortUrlRepository;
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
        if (request.getIsCustomRequested()) {
            shortened = Shortener.builder()
                    .urlName(request.getUrlName())
                    .longUrl(request.getLongUrl())
                    .tinyUrl(shortenUrl(request.getLongUrl()))
                    .shortenedBitlyUrl(shortenUrlWithBitly(request.getLongUrl()))
                    .userEmail(request.getUserEmail())
                    .isCustomRequested(request.getIsCustomRequested())
                    .customUrl(generateAlias(request.getLongUrl()))
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
                    .isCustomRequested(request.getIsCustomRequested())
                    .customUrl(null)
                    .createdDate(new Date(currentTimeMillis))
                    .expiryDate(new Date(expiryTimeMillis))
                    .build();
        }
        urlRepository.save(shortened);
        return true;
    }

    public  String NewSHortenUrl(String longUrl) {
        String shortUrl = "";
        Bitly bitly = ly(BITLY_ACCESS_TOKEN);
        shortUrl = bitly.shorten(longUrl);
        return shortUrl;
    }
    public String shortenUrl(String longUrl) {
        StringBuilder tinyUrl = new StringBuilder();
        try {
            URL url = new URL("http://tinyurl.com/api-create.php?url=" + longUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                Scanner scanner = new Scanner(connection.getInputStream());
                while (scanner.hasNext()) {
                    tinyUrl.append(scanner.next());
                }
                scanner.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tinyUrl.toString();
    }
    public  String BitlyUrl(String Url) {
        StringBuilder shortUrl = new StringBuilder();
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\n    \"group_guid\": \"Bn4paPI0UVc\",\n    \"domain\": \"bit.ly\",\n    \"long_url\": \"https://interviewnoodle.com/url-shortening-system-architecture-d7ae5a7e0ce9\"\n}  \n");
            Request request = new Request.Builder()
                    .url("https://api-ssl.bitly.com/v4/shorten")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + BITLY_ACCESS_TOKEN)
                    .build();
            Response response = client.newCall(request).execute();
            shortUrl.append(response);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return shortUrl.toString();
    }

public String shortenUrlWithBitly(String longUrl) {
    StringBuilder shortUrl = new StringBuilder();
    try {
        URL url = new URL("https://api-ssl.bitly.com/v4/shorten");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + BITLY_ACCESS_TOKEN);
        connection.setDoOutput(true);
        String requestBody = "{\"group_guid\": \"Bn4paPI0UVc\", \"domain\": \"bit.ly\", \"long_url\": \""+longUrl+ "\" }";
        connection.getOutputStream().write(requestBody.getBytes());
        connection.connect();
        int responseCode = connection.getResponseCode();
        Scanner scanner;
        if (responseCode == 200) {
            scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNext()) {
                shortUrl.append(scanner.next());
            }
        }
        else {
            scanner = new Scanner(connection.getErrorStream());
            while (scanner.hasNext()) {
                System.out.println(scanner.next());
            }
        }
        scanner.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
    return shortUrl.toString();
}

    public String generateAlias(String longUrl) {
        // Generate a random alias
        String alias = RandomStringUtils.randomAlphanumeric(6);


        // Check if the alias is already in use, if yes, generate a new alias
        while (urlRepository.getCustomUrl(alias)) {
            alias = RandomStringUtils.randomAlphanumeric(6);
        }
        return alias;
    }

    public String getLongUrl(String alias) {
        Optional<Shortener> optionalShortUrl = shortUrlRepository.getLongUrlByAlias(alias);
        if (optionalShortUrl.isPresent()) {
            return optionalShortUrl.get().getLongUrl();
        } else {
            return null;
        }
    }

    public void incrementClickCount(String alias) {
        Optional<ShortCount> optionalShortUrl = shortUrlRepository.findById(alias);
        if (optionalShortUrl.isPresent()) {
            ShortCount shortUrl = optionalShortUrl.get();
            shortUrl.getNoOfClicked(shortUrl.getNoOfClicked().intValue() + 1);
            shortUrlRepository.(shortUrl);
        }
    }
}