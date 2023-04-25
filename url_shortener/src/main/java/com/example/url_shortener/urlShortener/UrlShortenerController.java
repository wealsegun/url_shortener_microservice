package com.example.url_shortener.urlShortener;
import com.example.url_shortener.url.Shortener;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RestController
@RequestMapping("/api/v1/short")
@RequiredArgsConstructor
public class UrlShortenerController {
    private  final  UrlShortenerService service;


    @PostMapping("/generate")
    public ResponseEntity<Boolean> GenerateTinyUrl(@RequestBody UrlUserRequest request) {
        return ResponseEntity.ok(service.createUserShortenedUrl(request));
    }

    @GetMapping("/link")
    public ResponseEntity<Shortener> GetShortenedUrlByUserEmail(String userEmail, Integer id) {
        return ResponseEntity.ok(service.getShortenedUrlById(userEmail, id));
    }
    @GetMapping("/links")
    public ResponseEntity<List<Shortener>> GetUrlsByUserEmail(String userEmail) {
        return ResponseEntity.ok(service.getShortenedUrlsByUserEmail(userEmail));
    }

//    @PostMapping("/shorten")
//    public String shortenUrl(@RequestBody String longUrl) {
//        // generate a unique short alias for the given long URL
////        String alias = generateAlias(longUrl);
//
//        // store the mapping between the alias and the long URL in a database or cache
////        saveUrlMapping(alias, longUrl);
//
//        // return the shortened URL to the client
//        return "http://mydomain.com/"; //+ alias;
//    }

//    @GetMapping("/{alias}")
//    public ResponseEntity<Void> redirectToLongUrl(@PathVariable String alias) {
//        // look up the long URL corresponding to the given alias in the database or cache
//        String longUrl = getLongUrl(alias);
//
//        if (longUrl == null) {
//            // if the alias is not found, return a 404 error to the client
//            return ResponseEntity.notFound().build();
//        } else {
//            // if the alias is found, redirect the client to the corresponding long URL
//            HttpHeaders headers = new HttpHeaders();
//            headers.setLocation(URI.create(longUrl));
//            return new ResponseEntity<Void>(headers, HttpStatus.MOVED_PERMANENTLY);
//        }
//    }


}
