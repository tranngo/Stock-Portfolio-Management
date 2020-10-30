<!DOCTYPE html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8" />
    <meta
      name="viewport"
      content="width=device-width, initial-scale=1, shrink-to-fit=no"
    />

    <!-- Bootstrap CSS -->
    <link
      rel="stylesheet"
      href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
      integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
      crossorigin="anonymous"
    />
    <link rel="stylesheet" type="text/css" href="css/home.css" />

    <link
      href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap"
      rel="stylesheet"
    />

    <title>USC CS310 Stock Portfolio Management</title>

    <script src="https://www.gstatic.com/charts/loader.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script
      src="https://kit.fontawesome.com/22f27996cf.js"
      crossorigin="anonymous"
    ></script>
  </head>
  <body>
    <!-- Modal -->
    <div id="mainModal" class="modal" tabindex="-1" role="dialog">
      <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Add Stock Transaction</h5>
            <button
              type="button"
              class="close"
              data-dismiss="modal"
              aria-label="Close"
            >
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <div id="add-modal-content">
              <form>
                <div class="row">
                  <div class="col">
                    <label for="stock-name-input">Stock (e.g. TSLA)</label>
                    <input
                      type="text"
                      id="stock-name-input"
                      class="form-control"
                    />
                  </div>
                  <div class="col">
                    <label for="stock-quantity-input">Quantity</label>
                    <input
                      type="number"
                      id="stock-quantity-input"
                      class="form-control"
                    />
                  </div>
                  <div class="col">
                    <label
                      id="stock-purchase-date-input-label"
                      for="stock-purchase-date-input"
                      >Purchase Date</label
                    >
                    <input
                      type="date"
                      id="stock-purchase-date-input"
                      class="form-control"
                    />
                  </div>
                  <div class="col">
                    <label
                      id="stock-sell-date-input-label"
                      for="stock-sell-date-input"
                      >Sell Date</label
                    >
                    <input
                      type="date"
                      id="stock-sell-date-input"
                      class="form-control"
                    />
                  </div>
                </div>
              </form>
            </div>
            <div id="upload-file-modal-content">
              <form>
                <div class="form-group">
                  <label for="uploadFile">Upload CSV file</label>
                  <input
                    type="file"
                    accept=".csv"
                    class="form-control-file"
                    id="uploadFile"
                  />
                </div>
              </form>
            </div>
            <div id="add-external-stock-modal-content">
              <form>
                <div class="row">
                  <div class="col">
                    <label for="add-external-stock-name-input"
                      >Stock (e.g. TSLA)</label
                    >
                    <input
                      type="text"
                      id="add-external-stock-name-input"
                      class="form-control"
                    />
                  </div>
                </div>
              </form>
            </div>
            <div id="remove-external-stock-modal-content">
              <form>
                <div class="row">
                  <div class="col">
                    <label for="remove-external-stock-name-input"
                      >Stock (e.g. TSLA)</label
                    >
                    <input
                      type="text"
                      id="remove-external-stock-name-input"
                      class="form-control"
                    />
                  </div>
                </div>
              </form>
            </div>
          </div>
          <div class="modal-footer">
            <button
              id="modal-confirm-button"
              type="button"
              class="btn btn-primary"
              data-dismiss="modal"
            >
              Confirm
            </button>
            <button
              id="modal-remove-button"
              type="button"
              class="btn btn-secondary"
              data-dismiss="modal"
            >
              Cancel
            </button>
          </div>
        </div>
      </div>
    </div>

    <nav class="navbar navbar-dark" id="navbar-bg">
      <a class="title bold" href="#"> USC CS310 Stock Portfolio Management </a>
      <a class="logout-btn" href="/LogoutServlet">Log out</a>
    </nav>
    <div class="container-fluid mb-5">
      <div class="row">
        <!-- Left column -->
        <div id="left-col" class="col-sm-12 col-md-2">
          <div
            class="d-flex flex-column justify-content-center mt-5 column-border text-center"
          >
            <h5 class="font-weight-bold">Portfolio</h5>

            <div
              class="stock-item d-flex flex-row justify-content-around align-items-center"
            >
              <i class="fas fa-times close-icon"></i>
              <p class="m-0 p-0 stock-name">TSLA</p>
              <i class="toggle-button fas fa-toggle-on fa-lg"></i>
            </div>

            <div
              class="stock-item d-flex flex-row justify-content-around align-items-center"
            >
              <i class="fas fa-times close-icon"></i>
              <p class="m-0 p-0 stock-name">AAPL</p>
              <i class="toggle-button fas fa-toggle-on fa-lg"></i>
            </div>
          </div>
          <div class="d-flex flex-column justify-content-center">
            <button
              type="button"
              id="add-stock-button"
              class="btn btn-primary mx-2 mt-2"
            >
              Add
            </button>
            <button
              type="button"
              id="upload-file-button"
              class="btn btn-primary mx-2 mt-2"
            >
              Upload
            </button>
          </div>
        </div>

        <!-- Chart column -->
        <div class="col-sm-12-12 col-md-8">
          <div class="chart" id="main-chart"></div>
          <div id="date-picker" class="text-center">
            <form onsubmit="return submitForm(event)">
              <input type="date" id="fromDate" name="fromDate" /> to
              <input type="date" id="toDate" name="toDate" />
              <button id="date-submit">Show me</button>
            </form>
          </div>
        </div>

        <!-- Right column -->
        <div id="right-col" class="col-sm-12 col-md-2">
          <div
            class="d-flex flex-column justify-content-center mt-5 column-border text-center"
          >
            <h5 class="font-weight-bold">External Stocks</h5>

            <ul id="external-stocks" class="text-center">
              <li>AAPL</li>
              <li>GOOG</li>
            </ul>

            <div class="d-flex flex-column justify-content-center">
              <button
                type="button"
                id="add-external-stock-button"
                class="btn btn-primary mx-2 mt-2"
              >
                Add
              </button>
              <button
                type="button"
                id="remove-external-stock-button"
                class="btn btn-primary mx-2 my-2"
              >
                Remove
              </button>
            </div>
          </div>

          <button
            type="button"
            id="sp-button"
            class="btn btn-primary mx-2 mt-5"
          >
            S&P
          </button>
        </div>
      </div>
    </div>

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script
      src="https://code.jquery.com/jquery-3.5.1.js"
      integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
      crossorigin="anonymous"
    ></script>
    <script
      src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
      integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
      crossorigin="anonymous"
    ></script>
    <script
      src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
      integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
      crossorigin="anonymous"
    ></script>
    <script src="js/home.js"></script>
  </body>
</html>
