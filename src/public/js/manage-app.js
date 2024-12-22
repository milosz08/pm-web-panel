'use strict';

const csrf = document.getElementById('csrf');

function loadingContainer(pmId) {
  return {
    container: document.getElementById(`waiting-${pmId}`),
    show() {
      this.container.classList.remove('d-none');
      this.container.classList.add('d-flex');
    },
    hide() {
      this.container.classList.remove('d-flex');
      this.container.classList.add('d-none');
    },
  }
}

function commonApiCall(action, pmId) {
  const waitingContainer = loadingContainer(pmId);
  waitingContainer.show();
  return new Promise(resolve => {
    fetch(`/api/${action}?pmId=${pmId}`, {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json'
      },
      credentials: 'same-origin',
      body: JSON.stringify({
        _csrf: csrf.value,
      }),
    })
      .then(res => res.json())
      .then(data => {
        waitingContainer.hide();
        const { csrf: resCsrf, ...rest } = data;
        csrf.value = resCsrf;
        resolve({ ...rest });
      })
      .catch(() =>  resolve({
        message: 'Unexpected error',
        status: 'error',
      }));
  });
}

function switchButtonsState(pmId, disabled) {
  const onDisabledBtn = document.querySelectorAll(`[data-on-disabled="${pmId}"]`);
  const onEnabledBtn = document.querySelectorAll(`[data-on-enabled="${pmId}"]`);
  for (const btn of onDisabledBtn) {
    btn.disabled = disabled;
  }
  for (const btn of onEnabledBtn) {
    btn.disabled = !disabled;
  }
}

function startApp(pmId) {
  commonApiCall('start', pmId).then(data => {
    switchButtonsState(pmId, true);
    window.toast.show(data);
  });
}

function reloadApp(pmId) {
  commonApiCall('reload', pmId).then(data => {
    window.toast.show(data);
  });
}

function restartApp(pmId) {
  commonApiCall('restart', pmId).then(data => {
    window.toast.show(data);
  });
}

function stopApp(pmId) {
  commonApiCall('stop', pmId).then(data => {
    switchButtonsState(pmId, false);
    window.toast.show(data);
  });
}

function deleteApp(pmId) {
  commonApiCall('delete', pmId).then(data => {
    if (data.status !== 'error') {
      window.location.href = '/';
    } else {
      window.toast.show(data);
    }
  });
}
