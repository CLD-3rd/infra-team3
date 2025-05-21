// 국가 리스트 동적 로딩
async function loadCountryOptions() {
  try {
    const response = await fetch('/api/countries', { credentials: 'same-origin' });
    if (!response.ok) throw new Error('국가 목록 조회 실패');
    const countries = await response.json();

    const select = document.getElementById('planCountry');
    select.innerHTML = '<option value="">여행지 선택</option>'; // 초기화

    countries.forEach(country => {
      select.innerHTML += `<option value="${country.countryName}">${country.flagEmoji || ''} ${country.countryName}</option>`;
    });
  } catch (err) {
    alert(err.message);
  }
}

// 플랜 목록 불러오기
function loadPlans() {
  fetch("/api/mypage/plan", {
    credentials: "same-origin"
  })
    .then(res => res.json())
    .then(data => {
      let html = "";
      data.forEach(plan => {
        html += `<tr>
          <td>${plan.countryEmoji ? plan.countryEmoji : ""} ${plan.countryName}</td>
          <td>${plan.travelDate}</td>
          <td>${plan.endDate}</td>
          <td>
            <button class="btn btn-sm btn-outline-danger" onclick="deletePlan(${plan.id})">삭제</button>
          </td>
        </tr>`;
      });
      document.getElementById("planListBody").innerHTML = html;
    })
    .catch(() => alert("로그인 필요 또는 서버 오류"));
}

// 플랜 추가
document.getElementById("addPlanForm").onsubmit = function(e) {
  e.preventDefault();
  const countryName = document.getElementById("planCountry").value;
  const travelDate = document.getElementById("planStart").value;
  const endDate = document.getElementById("planEnd").value;

  if (!countryName || !travelDate || !endDate) {
    alert("모든 항목을 입력하세요.");
    return;
  }

  fetch("/api/mypage/plan", {
    method: "POST",
    headers: {"Content-Type": "application/json"},
    credentials: "same-origin",
    body: JSON.stringify({ countryName, travelDate, endDate })
  })
    .then(res => res.json())
    .then(data => {
      alert(data.message);
      loadPlans();
      document.getElementById("addPlanForm").reset();
    })
    .catch(() => alert("등록 실패"));
};

// 플랜 삭제
function deletePlan(planId) {
  if (!confirm("정말 삭제하시겠습니까?")) return;
  fetch(`/api/mypage/plan/${planId}`, {
    method: "DELETE",
    credentials: "same-origin"
  })
    .then(res => res.json())
    .then(data => {
      alert(data.message);
      loadPlans();
    })
    .catch(() => alert("삭제 실패"));
}

// 모달이 열릴 때마다 플랜 목록과 국가 리스트 새로고침
document.getElementById('planModal').addEventListener('show.bs.modal', () => {
  loadPlans();
  loadCountryOptions();
});
