package com.example.url_shortener.urlShortener;

import com.example.url_shortener.url.ShortCount;
import com.example.url_shortener.url.ShortCountRepository;
import com.example.url_shortener.url.Shortener;
import com.example.url_shortener.url.URLShortenerRepository;
import com.example.url_shortener.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UrlShortenerService {
    private final UserRepository repository;
    private final URLShortenerRepository urlRepository;
    private final ShortCountRepository shortCountRepository;
    private static final String BITLY_ACCESS_TOKEN = "63b1526d98e86a26ea1cb06123d33cd2d43eb5b8"; // Replace with your Bitly access token

    public Shortener getShortenedUrlById(String userEmail, Integer id) {
        repository.findByEmail(userEmail).orElseThrow();
        return urlRepository.getShortenerById(id);
    }

    public List<UrlShortenerResponse> getShortenedUrlsByUserEmail(String userEmail) {
        List<UrlShortenerResponse> outputResponse = new ArrayList<>();
        repository.findByEmail(userEmail).orElseThrow();
        var response = urlRepository.getShortenerByUserEmail(userEmail);

        for (var element : response) {
            UrlShortenerResponse output = new UrlShortenerResponse();
            output.setId(element.getId());
            output.setUrlName(element.getUrlName());
            output.setLongUrl(element.getLongUrl());
            output.setTinyUrl(element.getTinyUrl());
            output.setShortenedBitlyUrl(element.getShortenedBitlyUrl());
            output.setUserEmail(element.getUserEmail());
            output.setIsCustomRequested(element.isCustomRequested());
            output.setClickedCount(shortCountByUserEmail(userEmail,element.getCustomUrl() ));
            output.setCustomUrl(element.getCustomUrl());
            output.setExpiryDate(element.getExpiryDate());
            output.setCreatedDate(element.getCreatedDate());

            outputResponse.add(output);
        }

        return outputResponse;
    }
    public boolean createUserShortenedUrl(UrlUserRequest request) {
        long currentTimeMillis = System.currentTimeMillis();
        long expiryTimeMillis = currentTimeMillis + (24 * 60 * 60 * 1000); // add 24 hours in milliseconds
        repository.findByEmail(request.getUserEmail()).orElseThrow();
        Shortener shortened;
        if (request.getIsCustomRequested()) {
            String alias = generateShortUrl(request.getLongUrl());
            shortened = Shortener.builder()
                    .urlName(request.getUrlName())
                    .longUrl(request.getLongUrl())
                    .tinyUrl(shortenUrl(request.getLongUrl()))
                    .shortenedBitlyUrl(shortenUrlWithBitly(request.getLongUrl()))
                    .userEmail(request.getUserEmail())
                    .isCustomRequested(request.getIsCustomRequested())
                    .customUrl(generateUrl(alias))
                    .alias(alias)
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

    public boolean createUserShortCount(ShortCountRequest request) {
        repository.findByEmail(request.getUserEmail()).orElseThrow();
        ShortCount shortCount;
        shortCount = ShortCount.builder()
                .userEmail(request.getUserEmail())
                .clickUrlName(request.getClickUrlName())
                .clickedLink(request.getClickedLink())
                .dateCreated(request.getDateCreated()).build();
        shortCountRepository.save(shortCount);
        return shortCount.getId() > 0;



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

    public String shortenUrlWithBitly(String longUrl) {
        StringBuilder shortUrl = new StringBuilder();
        try {
            URL url = new URL("https://api-ssl.bitly.com/v4/shorten");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + BITLY_ACCESS_TOKEN);
            connection.setDoOutput(true);
            String requestBody = "{\"group_guid\": \"Bn4paPI0UVc\", \"domain\": \"bit.ly\", \"long_url\": \"" + longUrl + "\" }";
            connection.getOutputStream().write(requestBody.getBytes());
            connection.connect();
            int responseCode = connection.getResponseCode();
            Scanner scanner;
            if (responseCode == 200) {
                scanner = new Scanner(connection.getInputStream());
                while (scanner.hasNext()) {
                    shortUrl.append(scanner.next());
                }
            } else {
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

    public String generateShortUrl(String longUrl) {
        // Generate a unique identifier or use an algorithm to create a short URL
        String uniqueIdentifier = generateUniqueIdentifier();
        // Encode the unique identifier to create the short URL
        return encodeShortUrl(uniqueIdentifier);
    }

    public String generateUrl(String alias) {
        return "http://localhost:8080/api/v1/short/"+ alias;
    }

    private String generateUniqueIdentifier() {
        // Generate a unique identifier using UUID
        UUID uuid = UUID.randomUUID();
        String uniqueIdentifier = uuid.toString();

        // Remove hyphens from the UUID
        uniqueIdentifier = uniqueIdentifier.replace("-", "");

        return uniqueIdentifier;
    }

    private String encodeShortUrl(String uniqueIdentifier) {
        // Encode the unique identifier using Base64
        byte[] bytes = uniqueIdentifier.getBytes();
        String encoded = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);

        // Truncate the encoded string to a desired length (e.g., 8 characters)
        int desiredLength = 8;
        if (encoded.length() > desiredLength) {
            encoded = encoded.substring(0, desiredLength);
        }

        return encoded;
    }

    public String getLongUrl(String alias) {
        Shortener shortener = urlRepository.findByAlias(alias);
        long currentTimeMillis = System.currentTimeMillis();
        ShortCountRequest request;
        request = ShortCountRequest.builder()
                .userEmail(shortener.getUserEmail())
                .clickUrlName(shortener.getUrlName())
                .clickedLink(shortener.getCustomUrl())
                .dateCreated(new Date(currentTimeMillis)).build();
        createUserShortCount(request);
        return shortener.getLongUrl();
    }


    public Integer shortCountByUserEmail(String userEmail, String clickedLink) {
        List<ShortCount> shortCounts = shortCountRepository.findAllByUserEmailAndClickedLink(userEmail, clickedLink);
        return shortCounts.size();
    }
}