//
//  UniversalTableViewController.swift
//  YocShowcaseSwift
//
//  Created by Stefan Markovic on 7.4.22.
//

import UIKit
import VisxSDK

class UniversalTableViewController: UIViewController {

    @IBOutlet weak var tableView: UITableView!
    let sampleText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."

    var adView: VisxAdView?
    let adCellRow = 6
    var cellHeight: CGFloat = 0

    override func viewDidLoad() {
        super.viewDidLoad()
        loadAd()
        // VisxTableViewCell need to be registered before using
        tableView.register(VisxTableViewCell.self, forCellReuseIdentifier: VisxTableViewCell.visxCellIdentifier)
    }

    fileprivate func loadAd() {
        // Initializes visxAdView
        // @param adUnit creative ID
        // @param appDomain publisher domain
        // @param delegate adViewDelegate, viewcontroller which is implementing VisxAdViewDelegate protocol
        // @param size dimensions of the creative. see VisxAdSize.h
        // @param isUniversal Boolean flag which determines if the creative is universal or not
        // For fixed size creative, change universal flag to false
        adView = VisxAdView(adUnit: "912268",
                            appDomain: "yoc.com",
                            adViewDelegate: self,
                            adSize: .kMediumRectangle300x250,
                            universal: true)
        adView?.load()
    }
}

extension UniversalTableViewController: VisxAdViewDelegate {
    // ViewController for presenting VisxAdView
    func viewControllerForPresentingVisxAdView() -> UIViewController {
        self
    }
    // Delegate method is called when the ad content is received for the first time and the VisxAdView has finished rendering the content.
    // @param visxAdView visxAdView with content
    // @param effect placement effect of the creative
    func visxAdViewDidInitialize(visxAdView: VisxAdView, effect: VisxPlacementEffect) {
        cellHeight = visxAdView.frame.size.height
        tableView.reloadData()
    }
    // Delegate method is called when the ad has been closed.
    // @param visxAdView the visxAdView which is closed.
    func visxAdViewClosed(visxAdView: VisxAdView) {
        cellHeight = 0
        tableView.reloadData()
    }
    // Delegate method is called when retrieving the ad content has failed for any reason and provides a detail error message.
    // @param visxAdView visxAdView the corresponding VisxAdView
    // @param message descriptive message about the error that occurred
    // @param code status code about the error that occurred
    func visxAdFailedWithError(visxAdView: VisxAdView, message: String, code: Int) {
        cellHeight = 0
        tableView.reloadData()
    }
    // Delegate method is called when dimensions of visxAdView are changed.
    // @param visxAdView visxAdView the corresponding VisxAdView
    // @param width new visxAdView width
    // @param height new visxAdView height
    func visxAdViewSizeChange(visxAdView: VisxAdView, width: CGFloat, height: CGFloat) {
        cellHeight = height
        tableView.reloadData()
    }
}

extension UniversalTableViewController: UITableViewDelegate, UITableViewDataSource {

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if (indexPath.row == adCellRow && adView != nil) {
            let adCell = tableView.dequeueReusableCell(withIdentifier: VisxTableViewCell.visxCellIdentifier) as! VisxTableViewCell
            adCell.showAd(adView: adView!, tableView: tableView)
            return adCell
        } else {
            let cell = tableView.dequeueReusableCell(withIdentifier: "demoCell", for: indexPath)
            cell.textLabel?.text = sampleText
            cell.textLabel?.numberOfLines = 2;
            return cell
        }
    }

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        20
    }

    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if indexPath.row == adCellRow {
            return cellHeight
        }
        return 70
    }
}
