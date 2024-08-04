# 1. Project description
This is a small REST API service that serves one endpoint that provides
all repositories for a specific user that are not forks and lists the branches
with SHA of most recent commit.

# 2. Prerequisites
GitHub's API is rate limited, to increase request limit, provide a token that
can be generated for a GitHub account at this URL after logging in:

```
https://github.com/settings/tokens
```

The token can be provided to the application as a VM option:

```
-Dapitoken=[YOUR TOKEN]
```

In case a token isn't provided - service will quickly face rate limiting.
More can be read here:

```
https://docs.github.com/en/rest/using-the-rest-api/rate-limits-for-the-rest-api
```

# 3. API
The only endpoint looks as follows:

`GET /api/repos?username=[YOUR USERNAME]`

Possible endpoint responses:

HTTP 200 OK, body:

```
[
    {
        "repositoryName": "",
        "ownerLogin": "",
        "branchInfo": [
            {
                "branchName": "",
                "commitSha": ""
            }
            ...
        ]
    }
    ...
]
```

HTTP 404 NOT FOUND, body:
```
{
    "status": "404",
    "message": "GitHub user not found"
}
```

HTTP 429 TOO MANY REQUESTS, body:
```
{
    "status": "429",
    "message": "GitHub request rate limit exceeded, provide api token for bigger limit"
}
```

HTTP 500 INTERNAL SERVER ERROR, body:
```
{
    "status": "500",
    "message": "Error processing JSON response from GitHub API"
}
```

HTTP 500 INTERNAL SERVER ERROR, body:
```
{
    "status": "500",
    "message": "Other error"
}
```