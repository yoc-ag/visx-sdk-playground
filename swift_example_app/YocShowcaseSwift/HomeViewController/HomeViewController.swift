//
//  ViewController.swift
//  YocShowcaseSwift
//
//  Created by Stefan Markovic on 7.4.22.
//

import UIKit

class HomeViewController: UIViewController {

    @IBOutlet weak var tableView: UITableView!

    let titles = ["Universal Creative TableView", "Universal Creative ScrollView", "Interstitial Creative"]

    override func viewDidLoad() {
        super.viewDidLoad()
    }
}

extension HomeViewController: UITableViewDelegate, UITableViewDataSource {

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        3
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "demoCell", for: indexPath)
        cell.textLabel?.text = titles[indexPath.row]
        return cell
    }

    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        switch indexPath.row {
        case 0:
            showVC(type: .universal, id: .universalTableViewController)
        case 1:
            showVC(type: .universal, id: .universalScrollViewController)
        case 2:
            showVC(type: .interstitial, id: .interstitialViewController)
        default:
            break
        }
    }
}

extension UIViewController {
    func showVC(type: creativeType, id: creativeViewControllerIdentifier) {
        let storyboard = UIStoryboard(name: "Main", bundle: nil)
        let vc = storyboard.instantiateViewController(withIdentifier: id.rawValue)
        self.navigationController?.pushViewController(vc, animated: true) 
    }
}

enum creativeType: String {
    case universal = "Universal"
    case interstitial = "Interstitial"
}

enum creativeViewControllerIdentifier: String {
    case universalTableViewController = "UniversalTableViewController"
    case universalScrollViewController = "UniversalScrollViewController"
    case interstitialViewController = "InterstitialViewController"
}
