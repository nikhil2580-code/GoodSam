# GoodSam

An Android app built for the GoodSam assignment — implements a Splash → Login → OTP → List screen flow using Clean Architecture, Hilt, Retrofit, and Kotlin Coroutines.

## Tech Stack

- **Language:** Kotlin
- **Architecture:** Clean Architecture (data / domain / presentation layers)
- **DI:** Hilt
- **Networking:** Retrofit + OkHttp + Gson
- **Async:** Kotlin Coroutines + Flow
- **UI:** XML Views + ViewBinding + ConstraintLayout
- **Image loading:** Glide
- **Build:** AGP 9.0.0, Gradle 9.0.0, JDK 17, compileSdk 36

## App Flow

1. **Splash Screen** — calls the device registration API on launch, then navigates to Login.
2. **Login Screen** — user enters a mobile number, triggers the sign-in API, and receives an OTP.
3. **OTP Screen** — user enters the 4-digit OTP to verify and log in.
4. **List Screen** — displays a list of users fetched from a public API, with avatar, name, email, and phone.

## Project Structure

```
com.nikhilkhairnar.goodsam
├── data
│   ├── remote
│   │   ├── ApiService.kt
│   │   └── dto/              # Request/response DTOs, mapped from real API responses
│   └── repository/           # Repository implementations — DTO ↔ domain mapping lives here
├── domain
│   ├── model/                # Clean domain models (User, AppResult, etc.)
│   ├── repository/           # Repository interfaces
│   └── usecase/               # One class per action (RegisterDevice, SignIn, VerifyOtp, GetUsers)
├── presentation
│   ├── splash/
│   ├── login/
│   ├── otp/
│   └── list/
└── di/                        # Hilt modules (NetworkModule, RepositoryModule)
```

**Dependency rule:** `presentation` depends on `domain`; `data` depends on `domain`; `domain` depends on nothing else. ViewModels only ever see `AppResult<T>` and domain models — never Retrofit types or DTOs directly.

## APIs Used

| # | Endpoint | Purpose | Status |
|---|----------|---------|--------|
| 1 | `POST /device` | Register device on app launch | ✅ Working |
| 2 | `POST /signin` | Send mobile number, receive OTP | ✅ Working |
| 3 | `POST /checkotp` | Verify OTP and log in | ⚠️ Blocked — see Known Issues |
| 4 | `GET https://dummyjson.com/users` | Fetch user list | ✅ Working |

All response shapes were verified empirically against the real server via `HttpLoggingInterceptor`, since no sample responses were provided in the original spec. APIs 1–3 use a custom envelope: `{"data": ..., "error": {...} | null}`. Note that this API always returns HTTP 200 even on failure — success/failure must be checked via the `error` field in the body, not the HTTP status code.

## Known Issues

### `checkotp` — "Invalid Passward"

The `checkotp` API consistently rejects the request with `{"error":{"code":3,"message":"Invalid Passward"}}`, regardless of what value is sent in the `password` field:
- `password: ""` (as shown in the original request template) — rejected
- `password: <the OTP value from signin>` — rejected

No step in the flow (`device` or `signin`) returns a password value, and the test mobile number used was never registered through any signup/password-setting step. This was flagged to the API owner; the app is fully built and wired for this step (`OtpViewModel.onVerifyClicked()`), and only needs the correct `password` value plugged in once clarified.

Every other part of the app — Splash, device registration, sign-in/OTP request, OTP screen UI (including auto-advancing 4-digit input), and the user list screen — is fully implemented and tested end-to-end.

## Setup

1. Clone the repo.
2. Open in Android Studio (Otter 3 Feature Drop or later recommended).
3. Ensure JDK 17+ is configured (`File → Project Structure → SDK Location`).
4. Sync Gradle.
5. Run on an emulator or device (min SDK 24).

No API keys or secrets are required — all endpoints are public/test endpoints as provided in the assignment.

## Testing Notes

Each layer (network, repository/use case, and full UI flow) was manually verified against the live server via Logcat before building the next layer on top of it — response shapes for APIs 1, 2, and 4 were confirmed via raw JSON logging rather than assumed, since the original assignment didn't include sample responses.
