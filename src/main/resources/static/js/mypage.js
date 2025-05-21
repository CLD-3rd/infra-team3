document.addEventListener("DOMContentLoaded", async function () {
  const nicknameElements = document.querySelectorAll("[data-id='nickname']");
  const createdAtElement = document.getElementById("createdAt");

  try {
    const response = await fetch("/api/mypage",{ 
		credentials: "same-origin" // 테스트용
	});
	
    const data = await response.json();

    if (response.ok) {
      nicknameElements.forEach(el => el.textContent = data.nickname);
      createdAtElement.textContent = new Date(data.createdAt).toLocaleString();

      // 모달 닉네임 입력란에도 기본값 세팅
      const nicknameInput = document.getElementById("nickname");
      if (nicknameInput) nicknameInput.value = data.nickname;
    } else {
      nicknameElements.forEach(el => el.textContent = "로그인 필요");
      createdAtElement.textContent = "-";
    }
  } catch (error) {
    nicknameElements.forEach(el => el.textContent = "조회 실패");
    createdAtElement.textContent = "-";
    console.error("마이페이지 조회 오류:", error);
  }
});
